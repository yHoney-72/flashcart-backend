package com.flashcart.flashcart_backend.deliverytracking.entity;

import com.flashcart.flashcart_backend.driver.entity.Driver;
import com.flashcart.flashcart_backend.enums.driver.DeliveryStatus;
import com.flashcart.flashcart_backend.order.entity.Order;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "delivery_tracking")
public class DeliveryTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false, name = "driver_latitude")
    private Double driverLatitude;

    @Column(nullable = false, name = "driver_longitude")
    private Double driverLongitude;

    @Column(nullable = false, name = "customer_latitude")
    private Double customerLatitude;

    @Column(nullable = false, name = "customer_longitude")
    private Double customerLongitude;

    @Column(name = "remaining_distance_km")
    private Double remainingDistanceKm;

    @Column(name = "eta_minutes")
    private Integer etaMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "delivery_status")
    private DeliveryStatus deliveryStatus = DeliveryStatus.ASSIGNED;
}