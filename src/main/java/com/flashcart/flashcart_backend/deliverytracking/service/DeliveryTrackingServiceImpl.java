package com.flashcart.flashcart_backend.deliverytracking.service;

import com.flashcart.flashcart_backend.deliverytracking.dto.DeliveryTrackingRequest;
import com.flashcart.flashcart_backend.deliverytracking.dto.DeliveryTrackingResponse;
import com.flashcart.flashcart_backend.deliverytracking.entity.DeliveryTracking;
import com.flashcart.flashcart_backend.deliverytracking.mapper.DeliveryTrackingMapper;
import com.flashcart.flashcart_backend.deliverytracking.repository.DeliveryTrackingRepository;
import com.flashcart.flashcart_backend.driver.entity.Driver;
import com.flashcart.flashcart_backend.driver.repository.DriverRepository;
import com.flashcart.flashcart_backend.exception.DeliveryTrackingAlreadyExistsException;
import com.flashcart.flashcart_backend.exception.ResourceNotFoundException;
import com.flashcart.flashcart_backend.order.entity.Order;
import com.flashcart.flashcart_backend.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryTrackingServiceImpl implements  DeliveryTrackingService{

    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final DeliveryTrackingMapper deliveryTrackingMapper;
    private final DeliveryTrackingRepository deliveryTrackingRepository;

    @Override
    public DeliveryTrackingResponse createDeliveryTracking(Long orderId, Long driverId, DeliveryTrackingRequest request) {
     Order order = getOrderById(orderId);
     Driver driver = getDriverById(driverId);
       deliveryTrackingRepository.findByOrder(order).ifPresent(existingTracking ->{throw new DeliveryTrackingAlreadyExistsException("Delivery tracking already exists for order with id: " + orderId);});
      DeliveryTracking deliveryTracking = deliveryTrackingMapper.toEntity(request);
      deliveryTracking.setOrder(order);
        deliveryTracking.setDriver(driver);
       deliveryTracking.setCustomerLatitude(order.getDeliveryLatitude());
       deliveryTracking.setCustomerLongitude(order.getDeliveryLongitude());
       DeliveryTracking savedTracking = deliveryTrackingRepository.save(deliveryTracking);
         return deliveryTrackingMapper.toResponse(savedTracking);
    }

    @Override
    public DeliveryTrackingResponse getDeliveryTrackingById(Long orderId) {
        Order order = getOrderById(orderId);
            DeliveryTracking deliveryTracking = getDeliveryTrackingByOrder(order);
            return deliveryTrackingMapper.toResponse(deliveryTracking);
    }

    @Override
    public DeliveryTrackingResponse updateDeliveryTracking(Long orderId, DeliveryTrackingRequest request) {
        Order order = getOrderById(orderId);
        DeliveryTracking deliveryTracking = getDeliveryTrackingByOrder(order);
        deliveryTrackingMapper.updateEntityFromRequest(request , deliveryTracking);
        DeliveryTracking updatedTracking = deliveryTrackingRepository.save(deliveryTracking);
        return deliveryTrackingMapper.toResponse(updatedTracking);
    }
    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    private Driver getDriverById(Long driverId) {
        return driverRepository.findById(driverId).orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + driverId));
    }
    private DeliveryTracking getDeliveryTrackingByOrder(Order order) {
        return deliveryTrackingRepository.findByOrder(order)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery tracking not found for order with id: " + order.getId()));
    }
}
