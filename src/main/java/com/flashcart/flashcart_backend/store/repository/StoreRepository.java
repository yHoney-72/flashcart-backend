package com.flashcart.flashcart_backend.store.repository;

import com.flashcart.flashcart_backend.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository  extends JpaRepository<Store,Long> {
    List<Store> findByIsDeletedFalse();

    Optional<Store> findByIdAndIsDeletedFalse(Long id);
}
