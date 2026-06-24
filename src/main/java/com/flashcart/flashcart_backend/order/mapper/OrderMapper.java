package com.flashcart.flashcart_backend.order.mapper;

import com.flashcart.flashcart_backend.order.dto.*;
import com.flashcart.flashcart_backend.order.entity.Order;
import com.flashcart.flashcart_backend.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(CreateOrderRequest request);
    @Mapping(target = "storeName", source = "store.storeName")
    OrderSummaryResponse toSummaryResponse(Order order);
    OrderDetailsResponse toDetailResponse(Order order);
    OrderItemDetailsResponse toItemDetailResponse(OrderItem orderItem);
    CreateOrderResponse toCreateOrderResponse(Order order);
}
