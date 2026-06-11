package com.flashcart.flashcart_backend.product.controller;

import com.flashcart.flashcart_backend.product.dto.ProductRequestDTO;
import com.flashcart.flashcart_backend.product.dto.ProductResponseDTO;
import com.flashcart.flashcart_backend.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        return productService.save(productRequestDTO);
    }
    @GetMapping

    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> findAll(){
        return productService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO findById(@PathVariable Long id){
        return productService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STORE_OWNER')")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO update(@Valid @RequestBody ProductRequestDTO productRequestDTO ,  @PathVariable Long id){
        return productService.updateProduct(id, productRequestDTO);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STORE_OWNER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id ){
        productService.deleteProduct(id);
    }
}
