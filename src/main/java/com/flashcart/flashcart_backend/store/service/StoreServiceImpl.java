package com.flashcart.flashcart_backend.store.service;

import com.flashcart.flashcart_backend.enums.store.StoreStatus;
import com.flashcart.flashcart_backend.exception.ResourceNotFoundException;
import com.flashcart.flashcart_backend.store.dto.StoreRequestDTO;
import com.flashcart.flashcart_backend.store.dto.StoreResponseDTO;
import com.flashcart.flashcart_backend.store.entity.Store;
import com.flashcart.flashcart_backend.store.mapper.StoreMapper;
import com.flashcart.flashcart_backend.store.repository.StoreRepository;
import com.flashcart.flashcart_backend.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    @Override
    public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO) {
        Store store = storeMapper.toEntity(storeRequestDTO);
        store.setStatus(StoreStatus.PENDING_APPROVAL);
        store.setOwner(getCurrentUser());
        Store savedStore = storeRepository.save(store);
        return storeMapper.toResponse(savedStore);
    }

    @Override
    public List<StoreResponseDTO> findAllStores() {
        return storeRepository.findByIsDeletedFalse()
                .stream()
                .map(storeMapper::toResponse)
                .toList();

    }

    @Override
    public StoreResponseDTO findById(Long id) {
        Store store = getStoreById(id);
        return storeMapper.toResponse(store);
    }

    @Override
    public void deleteStore(Long id) {
        Store store = getStoreById(id);
        store.setIsDeleted(true);
        store.setDeletedAt(LocalDateTime.now());
        storeRepository.save(store);
    }

    @Override
    public StoreResponseDTO updateStore(Long id, StoreRequestDTO storeRequestDTO) {
        Store store = getStoreById(id);
        validateStoreOwnership(store);
        storeMapper.updateStore(store, storeRequestDTO);
        Store updatedStore = storeRepository.save(store);
        return storeMapper.toResponse(updatedStore);
    }

    private void validateStoreOwnership(Store store) {
        User currentUser = getCurrentUser();
        if (!store.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    "You are not allowed to access this store");
        }
    }

    @Override
    public StoreResponseDTO approveStore(Long id) {
        Store store = getStoreById(id);
        store.setStatus(StoreStatus.APPROVED);
        Store approvedStore = storeRepository.save(store);
        return storeMapper.toResponse(approvedStore);
    }

    @Override
    public StoreResponseDTO rejectStore(Long id) {
        Store store = getStoreById(id);
        store.setStatus(StoreStatus.REJECTED);
        Store rejectedStore = storeRepository.save(store);
        return storeMapper.toResponse(rejectedStore);
    }

    private Store getStoreById(Long id) {
        return storeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + id));
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }
}

