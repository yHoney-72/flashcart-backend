package com.flashcart.flashcart_backend.store.controller;

import com.flashcart.flashcart_backend.store.dto.StoreRequestDTO;
import com.flashcart.flashcart_backend.store.dto.StoreResponseDTO;
import com.flashcart.flashcart_backend.store.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public StoreResponseDTO createStore(@Valid @RequestBody StoreRequestDTO storeRequestDTO) {
        return storeService.createStore(storeRequestDTO);
    }
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<StoreResponseDTO> getAllStores() {
        return storeService.findAllStores();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDTO getStore(@PathVariable Long id) {
        return storeService.findById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STORE_OWNER')")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDTO updateStore(@Valid @RequestBody StoreRequestDTO storeRequestDTO,@PathVariable Long id) {
return storeService.updateStore(id, storeRequestDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
    }


    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDTO approveStore(@PathVariable Long id){
        return storeService.approveStore(id);
    }


    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDTO rejectStore(@PathVariable Long id){
        return storeService.rejectStore(id);
    }
}
