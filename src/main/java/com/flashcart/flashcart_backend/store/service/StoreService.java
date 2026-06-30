package com.flashcart.flashcart_backend.store.service;

import com.flashcart.flashcart_backend.store.dto.StoreRequestDTO;
import com.flashcart.flashcart_backend.store.dto.StoreResponseDTO;

import java.util.List;

public interface StoreService {
    StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO);

    List<StoreResponseDTO> findAllStores();

    StoreResponseDTO findById(Long id);

    void deleteStore(Long id);

    StoreResponseDTO updateStore(Long id, StoreRequestDTO storeRequestDTO);

    StoreResponseDTO approveStore(Long id);

    StoreResponseDTO rejectStore(Long id);
}
