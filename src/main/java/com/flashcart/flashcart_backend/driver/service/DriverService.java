package com.flashcart.flashcart_backend.driver.service;

import com.flashcart.flashcart_backend.driver.dto.*;
import com.flashcart.flashcart_backend.driver.entity.Driver;
import com.flashcart.flashcart_backend.driver.mapper.DriverMapper;
import com.flashcart.flashcart_backend.driver.repository.DriverRepository;
import com.flashcart.flashcart_backend.enums.user.Login;
import com.flashcart.flashcart_backend.enums.user.Role;
import com.flashcart.flashcart_backend.exception.*;
import com.flashcart.flashcart_backend.order.entity.Order;
import com.flashcart.flashcart_backend.order.repository.OrderRepository;
import com.flashcart.flashcart_backend.user.entity.User;
import com.flashcart.flashcart_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final DriverMapper driverMapper;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    public DriverResponse registerDriver(DriverRegistrationRequest request) {
        validateDriverRegistration(request);
        User user = driverMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.DELIVERY_AGENT);
        user.setLoginType(Login.EMAIL);
        User savedUser = userRepository.save(user);
        Driver driver = driverMapper.toDriverEntity(request);
        driver.setUser(savedUser);
        Driver savedDriver = driverRepository.save(driver);
        return driverMapper.toDriverResponse(savedDriver);
    }

    public DriverProfileResponse getDriverProfile(Long driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->new ResourceNotFoundException("Driver not found with id: " + driverId));
        return driverMapper.toDriverProfileResponse(driver);
    }

    public DriverProfileResponse updateDriverProfile(Long driverId,UpdateDriverProfileRequest request) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(() ->new ResourceNotFoundException("Driver not found with id: " + driverId));
        driverMapper.updateDriverFromRequest(request, driver);
        Driver updatedDriver = driverRepository.save(driver);
        return driverMapper.toDriverProfileResponse(updatedDriver);
    }
    public DriverProfileResponse updateDriverStatus(Long driverId, UpdateDriverStatusRequest request){
           Driver driver = driverRepository.findById(driverId).orElseThrow(() ->new ResourceNotFoundException("Driver not found with id: " + driverId));
              driver.setStatus(request.getStatus());
                Driver updatedDriver = driverRepository.save(driver);
                return driverMapper.toDriverProfileResponse(updatedDriver);

    }
    public DriverProfileResponse updateDriverAvailability(Long driverId,UpdateDriverAvailabilityRequest request){
        Driver driver = driverRepository.findById(driverId).orElseThrow(() ->new ResourceNotFoundException("Driver not found with id: " + driverId));
        driver.setAvailability(request.getAvailability());
        Driver updatedDriver = driverRepository.save(driver);
        return driverMapper.toDriverProfileResponse(updatedDriver);
    }
    public DriverProfileResponse verifyDriverStatus(Long driverId,VerifyDriverRequest request){
        Driver driver = driverRepository.findById(driverId).orElseThrow(() ->new ResourceNotFoundException("Driver not found with id: " + driverId));
        driver.setVerified(request.getVerified());
        Driver updateDriver = driverRepository.save(driver);
        return driverMapper.toDriverProfileResponse(updateDriver);
    }
   public DriverProfileResponse updateDriverLocation(Long driverId,UpdateDriverLocationRequest request){
       Driver driver = driverRepository.findById(driverId).orElseThrow(() ->new ResourceNotFoundException("Driver not found with id: " + driverId));
       driver.setCurrentLatitude(request.getCurrentLatitude());
       driver.setCurrentLongitude(request.getCurrentLongitude());
       Driver updateDriver = driverRepository.save(driver);
       return driverMapper.toDriverProfileResponse(updateDriver);

   }
    public DriverProfileResponse assignOrderToDriver(Long driverId,AssignOrderToDriverRequest request) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(() ->new ResourceNotFoundException("Driver not found with id: " + driverId));
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() ->new ResourceNotFoundException("Order not found with id: " + request.getOrderId()));
        order.setDriver(driver);
        orderRepository.save(order);
        return driverMapper.toDriverProfileResponse(driver);
    }

    private void validateDriverRegistration(DriverRegistrationRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email is already registered.");
        }
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new PhoneNumberAlreadyExistsException("Phone number is already registered.");
        }
        if(driverRepository.existsByLicenseNumber(request.getLicenseNumber())){
            throw new LicenseNumberAlreadyExistsException("License number is already registered.");
        }
        if(driverRepository.existsByVehicleNumber(request.getVehicleNumber())){
            throw new VehicleNumberAlreadyExistsException("Vehicle number is already registered.");
        }
    }
}
