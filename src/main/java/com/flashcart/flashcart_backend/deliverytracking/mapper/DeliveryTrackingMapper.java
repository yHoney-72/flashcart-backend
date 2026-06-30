package com.flashcart.flashcart_backend.deliverytracking.mapper;

import com.flashcart.flashcart_backend.deliverytracking.dto.DeliveryTrackingRequest;
import com.flashcart.flashcart_backend.deliverytracking.dto.DeliveryTrackingResponse;
import com.flashcart.flashcart_backend.deliverytracking.entity.DeliveryTracking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeliveryTrackingMapper {
    DeliveryTracking toEntity(DeliveryTrackingRequest request);

    DeliveryTrackingResponse toResponse(DeliveryTracking deliveryTracking);

    void updateEntityFromRequest(DeliveryTrackingRequest request, @MappingTarget DeliveryTracking deliveryTracking);
}
