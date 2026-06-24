package com.flashcart.flashcart_backend.store.controller;

import com.flashcart.flashcart_backend.store.dto.StoreRequestDTO;
import com.flashcart.flashcart_backend.store.dto.StoreResponseDTO;
import com.flashcart.flashcart_backend.store.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;
    public StoreController( StoreService storeService) {
     this.storeService = storeService;
    }
    @PostMapping()
    @PreAuthorize("hasRole('STORE_OWNER')")
    public ResponseEntity<StoreResponseDTO> createStore(@Valid @RequestBody StoreRequestDTO storeRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.createStore(storeRequestDTO));
    }
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StoreResponseDTO>> getAllStores() {
        return ResponseEntity.ok(storeService.findAllStores());
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoreResponseDTO> getStore(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.findById(id));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STORE_OWNER')")
    public ResponseEntity<StoreResponseDTO> updateStore(@Valid @RequestBody StoreRequestDTO storeRequestDTO,@PathVariable Long id) {
        return ResponseEntity.ok(storeService.updateStore(id, storeRequestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoreResponseDTO> approveStore(@PathVariable Long id){
        return ResponseEntity.ok(storeService.approveStore(id));
    }


    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoreResponseDTO> rejectStore(@PathVariable Long id){
        return ResponseEntity.ok(storeService.rejectStore(id));
    }
}
