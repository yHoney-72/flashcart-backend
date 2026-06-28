package com.flashcart.flashcart_backend.driver.repository;

import com.flashcart.flashcart_backend.driver.entity.Driver;
import com.flashcart.flashcart_backend.enums.driver.DriverAvailability;
import com.flashcart.flashcart_backend.enums.driver.DriverStatus;
import com.flashcart.flashcart_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository  extends JpaRepository<Driver, Long> {
    Optional<Driver> findByUser(User user);
    Optional<Driver> findByLicenseNumber(String LicenseNumber);
    boolean existsByVehicleNumber(String vehicleNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    List<Driver> findByStatusAndAvailability(DriverStatus status, DriverAvailability availability);
}
