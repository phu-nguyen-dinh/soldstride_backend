package com.solestride.controller;

import com.solestride.dto.AdminDto;
import com.solestride.dto.OrderDto;
import com.solestride.dto.ProductDto;
import com.solestride.entity.User;
import com.solestride.service.AdminService;
import com.solestride.service.OrderService;
import com.solestride.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final AdminService adminService;
    private final ProductService productService;
    private final OrderService orderService;
    
    @GetMapping("/stats")
    public ResponseEntity<AdminDto.DashboardStats> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }
    
    @GetMapping("/inventory")
    public ResponseEntity<List<AdminDto.InventoryItem>> getInventory() {
        return ResponseEntity.ok(adminService.getInventory());
    }
    
    // Product Management
    @PostMapping("/products")
    public ResponseEntity<ProductDto.ProductResponse> createProduct(@Valid @RequestBody ProductDto.ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto.ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto.ProductRequest request
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }
    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    // User Management
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    // Order Management
    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<OrderDto.OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderDto.UpdateOrderStatusRequest request
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, request.getStatus()));
    }

    // Order Management - Get all orders for admin
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto.OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

}
