package com.flashcart.flashcart_backend.deliverytracking.repository;

import com.flashcart.flashcart_backend.deliverytracking.entity.DeliveryTracking;
import com.flashcart.flashcart_backend.driver.entity.Driver;
import com.flashcart.flashcart_backend.enums.driver.DeliveryStatus;
import com.flashcart.flashcart_backend.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, Long> {

    Optional<DeliveryTracking> findByOrder(Order order);

    Optional<DeliveryTracking> findByDriver(Driver driver);

    List<DeliveryTracking> findByDeliveryStatus(DeliveryStatus deliveryStatus);


    // Optional.orElseThrow() -> Use when the object is required; throw exception if absent.
// Optional.ifPresent() -> Use when the object should NOT already exist; throw exception if present.
// Both methods are called on Optional, but they solve opposite business cases.
}
