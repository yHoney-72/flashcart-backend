package com.flashcart.flashcart_backend.store.mapper;

import com.flashcart.flashcart_backend.store.dto.StoreRequestDTO;
import com.flashcart.flashcart_backend.store.dto.StoreResponseDTO;
import com.flashcart.flashcart_backend.store.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    Store toEntity(StoreRequestDTO dto);
    StoreResponseDTO toResponse(Store store);
   void  updateStore(@MappingTarget Store store, StoreRequestDTO dto);
}
