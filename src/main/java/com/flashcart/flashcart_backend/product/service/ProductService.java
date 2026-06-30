package com.flashcart.flashcart_backend.product.service;

import com.flashcart.flashcart_backend.product.dto.ProductRequestDTO;
import com.flashcart.flashcart_backend.product.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO save(ProductRequestDTO productRequestDTO);

    ProductResponseDTO findById(Long id);

    List<ProductResponseDTO> findAll();

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);

    void deleteProduct(Long id);
}
