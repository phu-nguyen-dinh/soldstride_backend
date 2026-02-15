package com.solestride.dto;

import com.solestride.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AdminDto {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardStats {
        private Double revenue;
        private Integer ordersCount;
        private Integer itemsSold;
        private Double cancelledRate;
        private List<DailyMetric> dailyRevenue;
        private List<DailyMetric> dailyOrders;
        private List<StatusDistribution> statusDistribution;
        private List<OrderDto.OrderResponse> recentOrders;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyMetric {
        private String date;
        private Double value;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusDistribution {
        private String name;
        private Integer value;
        private String color;
        
        public StatusDistribution(OrderStatus status, Integer value) {
            this.name = status.name();
            this.value = value;
            this.color = getColorForStatus(status);
        }
        
        private String getColorForStatus(OrderStatus status) {
            return switch (status) {
                case PENDING -> "#FFA500";
                case PROCESSING -> "#1E90FF";
                case SHIPPED -> "#32CD32";
                case DELIVERED -> "#228B22";
                case CANCELLED -> "#DC143C";
            };
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventoryItem {
        private Long productId;
        private String productName;
        private String sku;
        private String color;
        private Double size;
        private Integer stock;
        private String imageUrl;
    }
}
