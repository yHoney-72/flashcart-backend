package com.flashcart.flashcart_backend.driver.entity;

import com.flashcart.flashcart_backend.enums.driver.DriverAvailability;
import com.flashcart.flashcart_backend.enums.driver.DriverStatus;
import com.flashcart.flashcart_backend.enums.driver.VehicleType;
import com.flashcart.flashcart_backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "drivers")
public class Driver {

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
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true, name = "license_number")
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "vehicle_type")
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverStatus status = DriverStatus.OFFLINE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverAvailability availability = DriverAvailability.UNAVAILABLE;

    @Column(nullable = false, name = "is_verified")
    private Boolean verified = false;

    private Double rating;

    @Column(nullable = false, name = "rating_count")
    private Integer ratingCount = 0;

    @Column(nullable = false, name = "total_deliveries")
    private Integer totalDeliveries = 0;

    @Column(name = "current_latitude")
    private Double currentLatitude;

    @Column(name = "current_longitude")
    private Double currentLongitude;

    @Column(nullable = false, name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(nullable = false, unique = true, name = "vehicle_number")
    private String vehicleNumber;
}