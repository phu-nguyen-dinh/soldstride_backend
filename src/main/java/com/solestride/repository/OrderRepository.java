package com.solestride.repository;

import com.solestride.entity.Order;
import com.solestride.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId);
    
    List<Order> findTop10ByOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countByStatus(OrderStatus status);
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.status != 'CANCELLED'")
    Double calculateTotalRevenue();
    
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :startDate")
    List<Order> findOrdersSince(LocalDateTime startDate);
    
    @Query("SELECT SUM(oi.quantity) FROM Order o JOIN o.items oi WHERE o.status != 'CANCELLED'")
    Long calculateTotalItemsSold();
}
