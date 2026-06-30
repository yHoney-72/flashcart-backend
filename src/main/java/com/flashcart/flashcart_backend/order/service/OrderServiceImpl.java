package com.flashcart.flashcart_backend.order.service;

import com.flashcart.flashcart_backend.enums.orders.OrderStatus;
import com.flashcart.flashcart_backend.exception.OutOfStockException;
import com.flashcart.flashcart_backend.exception.ResourceNotFoundException;
import com.flashcart.flashcart_backend.order.dto.*;
import com.flashcart.flashcart_backend.order.entity.Order;
import com.flashcart.flashcart_backend.order.entity.OrderItem;
import com.flashcart.flashcart_backend.order.mapper.OrderMapper;
import com.flashcart.flashcart_backend.order.repository.OrderItemRepository;
import com.flashcart.flashcart_backend.order.repository.OrderRepository;
import com.flashcart.flashcart_backend.product.entity.Product;
import com.flashcart.flashcart_backend.product.repository.ProductRepository;
import com.flashcart.flashcart_backend.store.entity.Store;
import com.flashcart.flashcart_backend.store.repository.StoreRepository;
import com.flashcart.flashcart_backend.user.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        User customer = getCurrentUser();
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        Order order = orderMapper.toEntity(request);
        order.setStore(store);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PLACED);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = getProductById(itemRequest.getProductId());
            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new OutOfStockException("Insufficient stock available for product: " + product.getName());
            }
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(subtotal);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setSubtotal(subtotal);
            orderItems.add(orderItem);
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
        }
        order.setTrackingId(generateTrackingId());
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        return orderMapper.toCreateOrderResponse(savedOrder);
    }

    private String generateTrackingId() {
        return "ORD-" + java.util.UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    @Override
    public List<OrderSummaryResponse> getMyOrders() {
        User customer = getCurrentUser();
        return orderRepository.findByCustomer(customer)
                .stream()
                .map(orderMapper::toSummaryResponse)
                .toList();
    }

    @Override
    public OrderDetailsResponse getOrderDetails(String trackingId) {
        Order order = getOrderByTrackingId(trackingId);
        validateOrderOwnership(order);
        return orderMapper.toDetailResponse(order);
    }

    @Override
    @Transactional
    public void cancelOrder(String trackingId) {
        Order order = getOrderByTrackingId(trackingId);
        validateOrderOwnership(order);
        if (order.getStatus() == OrderStatus.PLACED || order.getStatus() == OrderStatus.CONFIRMED || order.getStatus() == OrderStatus.PACKING) {
            order.setStatus(OrderStatus.CANCELLED);
            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            }
            orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order can't be cancelled");
        }
    }

    @Override
    public void updateOrderStatus(String trackingId, UpdateOrderStatusRequest request) {
        Order order = getOrderByTrackingId(trackingId);
        OrderStatus currentStatus = order.getStatus();
        OrderStatus requestedStatus = request.getStatus();
        if (!currentStatus.canTransitionTo(requestedStatus)) {
            throw new IllegalStateException("Cannot transition order status from " + currentStatus + " to " + requestedStatus);
        }
        order.setStatus(requestedStatus);
        orderRepository.save(order);
    }

    private void validateOrderOwnership(Order order) {
        User currentUser = getCurrentUser();
        if (!order.getCustomer().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }
    }

    private Order getOrderByTrackingId(String trackingId) {
        return orderRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }
}

