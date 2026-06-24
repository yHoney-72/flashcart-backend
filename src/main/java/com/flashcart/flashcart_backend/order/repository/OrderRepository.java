package com.flashcart.flashcart_backend.order.repository;

import com.flashcart.flashcart_backend.order.entity.Order;
import com.flashcart.flashcart_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order>findByCustomer(User customer);
    Optional<Order> findByTrackingId(String trackingId);
}
