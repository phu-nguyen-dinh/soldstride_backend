package com.solestride.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ProductDto {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductVariantDto {
        private Double size;
        private String color;
        private Integer stock;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductResponse {
        private Long id;
        private String name;
        private String brand;
        private Double price;
        private String description;
        private String imageUrl;
        private String category;
        private Boolean featured;
        private List<ProductVariantDto> variants;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductRequest {
        @NotBlank(message = "Name is required")
        private String name;
        
        @NotBlank(message = "Brand is required")
        private String brand;
        
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        private Double price;
        
        private String description;
        
        @NotBlank(message = "Image URL is required")
        private String imageUrl;
        
        @NotBlank(message = "Category is required")
        private String category;
        
        private Boolean featured = false;
        
        private List<ProductVariantDto> variants;
    }
}
