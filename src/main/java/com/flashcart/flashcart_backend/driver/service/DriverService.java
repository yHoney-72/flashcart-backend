package com.flashcart.flashcart_backend.driver.service;

import com.flashcart.flashcart_backend.driver.dto.*;

public interface DriverService {

    DriverResponse registerDriver(DriverRegistrationRequest request);

    DriverProfileResponse getDriverProfile(Long driverId);

    DriverProfileResponse updateDriverProfile(Long driverId, UpdateDriverProfileRequest request);

    DriverProfileResponse updateDriverStatus(Long driverId, UpdateDriverStatusRequest request);

    DriverProfileResponse updateDriverAvailability(Long driverId, UpdateDriverAvailabilityRequest request);

    DriverProfileResponse verifyDriverStatus(Long driverId, VerifyDriverRequest request);

    DriverProfileResponse updateDriverLocation(Long driverId, UpdateDriverLocationRequest request);

    DriverProfileResponse assignOrderToDriver(Long driverId, AssignOrderToDriverRequest request);
}
