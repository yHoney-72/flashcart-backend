package com.flashcart.flashcart_backend.product.controller;

import com.flashcart.flashcart_backend.product.dto.ProductRequestDTO;
import com.flashcart.flashcart_backend.product.dto.ProductResponseDTO;
import com.flashcart.flashcart_backend.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    @PreAuthorize("hasRole('STORE_OWNER')")
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productRequestDTO));
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STORE_OWNER')")
    public ResponseEntity<ProductResponseDTO> update(@Valid @RequestBody ProductRequestDTO productRequestDTO ,  @PathVariable Long id){
        return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STORE_OWNER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id ){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
