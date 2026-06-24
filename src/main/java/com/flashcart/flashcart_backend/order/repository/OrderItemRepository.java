package com.flashcart.flashcart_backend.order.repository;

import com.flashcart.flashcart_backend.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository  extends JpaRepository<OrderItem,Long> {
}
