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
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreService(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }
  public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Store store = storeMapper.toEntity(storeRequestDTO);
      store.setStatus(StoreStatus.PENDING_APPROVAL);
      store.setOwner((User) authentication.getPrincipal());
      Store savedStore = storeRepository.save(store);
      return storeMapper.toResponse(savedStore);
  }
  public List<StoreResponseDTO> findAllStores() {
     return storeRepository.findByIsDeletedFalse()
             .stream()
             .map(storeMapper::toResponse)
             .toList();

  }
  public StoreResponseDTO findById(Long id ) {
         Store store = storeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()->new ResourceNotFoundException("Store not found with id: " + id));
         return storeMapper.toResponse(store);
  }
  public void deleteStore(Long id ) {
        Store store = storeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()->new ResourceNotFoundException("Store not found with id: " + id));
        store.setIsDeleted(true);
        store.setDeletedAt(LocalDateTime.now());
        storeRepository.save(store);
  }
  public StoreResponseDTO updateStore(Long id, StoreRequestDTO storeRequestDTO) {
        Store store = storeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()->new ResourceNotFoundException("Store not found with id: " + id));
      validateStoreOwnership(store);
        storeMapper.updateStore(store, storeRequestDTO);
        Store updatedStore = storeRepository.save(store);
        return storeMapper.toResponse(updatedStore);
  }
    private void validateStoreOwnership(Store store) {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (!store.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    "You are not allowed to access this store");
        }
    }
    public StoreResponseDTO approveStore(Long id){
        Store store = storeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() ->new ResourceNotFoundException( "Store not found with id: " + id));
        store.setStatus(StoreStatus.APPROVED);
        store = storeRepository.save(store);
        return storeMapper.toResponse(store);
    }
    public StoreResponseDTO rejectStore(Long id){
        Store store = storeRepository .findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException( "Store not found with id: " + id));
        store.setStatus(StoreStatus.REJECTED);
        store = storeRepository.save(store);
        return storeMapper.toResponse(store);
    }
}
