package com.solestride.controller;

import com.solestride.dto.OrderDto;
import com.solestride.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    @GetMapping
    public ResponseEntity<List<OrderDto.OrderResponse>> getUserOrders() {
        return ResponseEntity.ok(orderService.getUserOrders());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto.OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    
    @PostMapping
    public ResponseEntity<OrderDto.OrderResponse> createOrder(@Valid @RequestBody OrderDto.CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }
}
