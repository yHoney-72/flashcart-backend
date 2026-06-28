package com.flashcart.flashcart_backend.driver.controller;

import com.flashcart.flashcart_backend.driver.dto.*;
import com.flashcart.flashcart_backend.driver.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/drivers")
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<DriverResponse> registerDriver(@Valid @RequestBody DriverRegistrationRequest request) {
        DriverResponse response = driverService.registerDriver(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{driverId}/profile")
    public ResponseEntity<DriverProfileResponse> getDriverProfile(@PathVariable Long driverId) {
        DriverProfileResponse response = driverService.getDriverProfile(driverId);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{driverId}/profile")
    public ResponseEntity<DriverProfileResponse> updateDriverProfile(@PathVariable Long driverId, @Valid @RequestBody UpdateDriverProfileRequest request) {
        DriverProfileResponse response = driverService.updateDriverProfile(driverId,request);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{driverId}/status")
    public ResponseEntity<DriverProfileResponse> updateDriverStatus(@PathVariable Long driverId, @Valid @RequestBody UpdateDriverStatusRequest request) {
        DriverProfileResponse response = driverService.updateDriverStatus(driverId,request);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{driverId}/availability")
    public ResponseEntity<DriverProfileResponse> updateDriverAvailability(@PathVariable Long driverId, @Valid @RequestBody UpdateDriverAvailabilityRequest request) {
        DriverProfileResponse response = driverService.updateDriverAvailability(driverId, request);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{driverId}/verify")
    public ResponseEntity<DriverProfileResponse> verifyDriverStatus(@PathVariable Long driverId ,@Valid @RequestBody VerifyDriverRequest request){
        DriverProfileResponse response = driverService.verifyDriverStatus(driverId,request);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{driverId}/location")
    public ResponseEntity<DriverProfileResponse> updateDriverLocation(@PathVariable Long driverId, @Valid @RequestBody UpdateDriverLocationRequest request) {
        DriverProfileResponse response =driverService.updateDriverLocation(driverId, request);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{driverId}/assign-order")
    public ResponseEntity<DriverProfileResponse> assignOrderToDriver(@PathVariable Long driverId,
            @Valid @RequestBody AssignOrderToDriverRequest request) {
        DriverProfileResponse response =driverService.assignOrderToDriver(driverId, request);
        return ResponseEntity.ok(response);
    }

}
