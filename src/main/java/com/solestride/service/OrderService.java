package com.solestride.service;

import com.solestride.dto.OrderDto;
import com.solestride.entity.Order;
import com.solestride.entity.OrderItem;
import com.solestride.entity.User;
import com.solestride.enums.OrderStatus;
import com.solestride.exception.BadRequestException;
import com.solestride.exception.ResourceNotFoundException;
import com.solestride.repository.OrderRepository;
import com.solestride.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    
    public List<OrderDto.OrderResponse> getUserOrders() {
        UUID userId = getCurrentUserId();
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public OrderDto.OrderResponse createOrder(OrderDto.CreateOrderRequest request) {
        UUID userId = getCurrentUserId();
        
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }
        
        Order order = new Order();
        order.setUserId(userId);
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        
        double total = 0.0;
        for (OrderDto.OrderItemDto itemDto : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductId(itemDto.getProductId());
            item.setName(itemDto.getName());
            item.setPrice(itemDto.getPrice());
            item.setQuantity(itemDto.getQuantity());
            item.setSize(itemDto.getSize());
            item.setColor(itemDto.getColor());
            item.setImageUrl(itemDto.getImageUrl());
            
            order.addItem(item);
            total += itemDto.getPrice() * itemDto.getQuantity();
        }
        
        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        
        return convertToResponse(savedOrder);
    }
    
    @Transactional
    public OrderDto.OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        
        return convertToResponse(updatedOrder);
    }
    
    public List<OrderDto.OrderResponse> getAllOrders() {
        return orderRepository
                .findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public OrderDto.OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        // Check if the order belongs to the current user or if the user is an admin
        UUID currentUserId = getCurrentUserId();
        if (!order.getUserId().equals(currentUserId) && !isAdmin()) {
            throw new BadRequestException("You don't have permission to view this order");
        }
        
        return convertToResponse(order);
    }
    
    private OrderDto.OrderResponse convertToResponse(Order order) {
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
    
    private UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getId();
    }
    
    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
}
