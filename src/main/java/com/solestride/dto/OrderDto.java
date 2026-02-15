package com.solestride.dto;

import com.solestride.entity.Address;
import com.solestride.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderDto {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDto {
        private Long productId;
        private String name;
        private Double price;
        private Integer quantity;
        private Double size;
        private String color;
        private String imageUrl;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderRequest {
        @NotNull(message = "Items are required")
        private List<OrderItemDto> items;
        
        @NotNull(message = "Shipping address is required")
        private Address shippingAddress;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderResponse {
        private Long id;
        private UUID userId;
        private List<OrderItemDto> items;
        private Double total;
        private OrderStatus status;
        private Address shippingAddress;
        private LocalDateTime createdAt;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateOrderStatusRequest {
        @NotNull(message = "Status is required")
        private OrderStatus status;
    }
}
