package com.solestride.service;

import com.solestride.dto.AdminDto;
import com.solestride.dto.OrderDto;
import com.solestride.entity.Order;
import com.solestride.entity.Product;
import com.solestride.entity.ProductVariant;
import com.solestride.entity.User;
import com.solestride.enums.OrderStatus;
import com.solestride.exception.ResourceNotFoundException;
import com.solestride.repository.OrderRepository;
import com.solestride.repository.ProductRepository;
import com.solestride.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    public AdminDto.DashboardStats getDashboardStats() {
        // Calculate revenue
        Double revenue = orderRepository.calculateTotalRevenue();
        if (revenue == null) revenue = 0.0;
        
        // Get order counts
        long totalOrders = orderRepository.count();
        long cancelledOrders = orderRepository.countByStatus(OrderStatus.CANCELLED);
        double cancelledRate = totalOrders > 0 ? ((double) cancelledOrders / totalOrders) * 100 : 0.0;
        
        // Get items sold
        Long itemsSold = orderRepository.calculateTotalItemsSold();
        if (itemsSold == null) itemsSold = 0L;
        
        // Get daily metrics (last 7 days)
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Order> recentOrders = orderRepository.findOrdersSince(sevenDaysAgo);
        
        List<AdminDto.DailyMetric> dailyRevenue = calculateDailyRevenue(recentOrders);
        List<AdminDto.DailyMetric> dailyOrders = calculateDailyOrders(recentOrders);
        
        // Get status distribution
        List<AdminDto.StatusDistribution> statusDistribution = Arrays.stream(OrderStatus.values())
                .map(status -> {
                    Long count = orderRepository.countByStatus(status);
                    return new AdminDto.StatusDistribution(status, count != null ? count.intValue() : 0);
                })
                .collect(Collectors.toList());
        
        // Get recent orders
        List<Order> topOrders = orderRepository.findTop10ByOrderByCreatedAtDesc();
        List<OrderDto.OrderResponse> recentOrderResponses = topOrders.stream()
                .map(this::convertOrderToResponse)
                .collect(Collectors.toList());
        
        return new AdminDto.DashboardStats(
                revenue,
                (int) totalOrders,
                itemsSold.intValue(),
                cancelledRate,
                dailyRevenue,
                dailyOrders,
                statusDistribution,
                recentOrderResponses
        );
    }
    
    public List<AdminDto.InventoryItem> getInventory() {
        List<Product> products = productRepository.findAll();
        List<AdminDto.InventoryItem> inventoryItems = new ArrayList<>();
        
        for (Product product : products) {
            for (ProductVariant variant : product.getVariants()) {
                String sku = String.format("SKU-%d-%s-%s", 
                    product.getId(), 
                    variant.getColor().replace(" ", "").toUpperCase(), 
                    variant.getSize().toString().replace(".", "_")
                );
                
                inventoryItems.add(new AdminDto.InventoryItem(
                        product.getId(),
                        product.getName(),
                        sku,
                        variant.getColor(),
                        variant.getSize(),
                        variant.getStock(),
                        product.getImageUrl()
                ));
            }
        }
        
        return inventoryItems;
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }
    
    private List<AdminDto.DailyMetric> calculateDailyRevenue(List<Order> orders) {
        Map<String, Double> dailyRevenueMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Initialize last 7 days with 0
        for (int i = 6; i >= 0; i--) {
            String date = LocalDate.now().minusDays(i).format(formatter);
            dailyRevenueMap.put(date, 0.0);
        }
        
        // Calculate revenue for each day
        for (Order order : orders) {
            if (order.getStatus() != OrderStatus.CANCELLED) {
                String date = order.getCreatedAt().toLocalDate().format(formatter);
                dailyRevenueMap.merge(date, order.getTotal(), Double::sum);
            }
        }
        
        return dailyRevenueMap.entrySet().stream()
                .map(entry -> new AdminDto.DailyMetric(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
    
    private List<AdminDto.DailyMetric> calculateDailyOrders(List<Order> orders) {
        Map<String, Long> dailyOrdersMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Initialize last 7 days with 0
        for (int i = 6; i >= 0; i--) {
            String date = LocalDate.now().minusDays(i).format(formatter);
            dailyOrdersMap.put(date, 0L);
        }
        
        // Count orders for each day
        for (Order order : orders) {
            String date = order.getCreatedAt().toLocalDate().format(formatter);
            dailyOrdersMap.merge(date, 1L, Long::sum);
        }
        
        return dailyOrdersMap.entrySet().stream()
                .map(entry -> new AdminDto.DailyMetric(entry.getKey(), entry.getValue().doubleValue()))
                .collect(Collectors.toList());
    }
    
    private OrderDto.OrderResponse convertOrderToResponse(Order order) {
        List<OrderDto.OrderItemDto> itemDtos = order.getItems().stream()
                .map(item -> new OrderDto.OrderItemDto(
                        item.getProductId(),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getSize(),
                        item.getColor(),
                        item.getImageUrl()
                ))
                .collect(Collectors.toList());
        
        return new OrderDto.OrderResponse(
                order.getId(),
                order.getUserId(),
                itemDtos,
                order.getTotal(),
                order.getStatus(),
                order.getShippingAddress(),
                order.getCreatedAt()
        );
    }
}
