package com.flashcart.flashcart_backend.product.dto;

import com.flashcart.flashcart_backend.enums.products.ProductCategory;
import lombok.Data;

import java.math.BigDecimal;
@Data

public class ProductResponseDTO {

    private String name;

    private ProductCategory category;

    private BigDecimal price;

    private Integer stockQuantity;

    private String description;

    private String imageUrl;

    private String sku;
    private Boolean isAvailable ;
    private Long storeId;
    private String storeName;
}