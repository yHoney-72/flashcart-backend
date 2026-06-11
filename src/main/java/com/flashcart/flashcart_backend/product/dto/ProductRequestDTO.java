package com.flashcart.flashcart_backend.product.dto;

import com.flashcart.flashcart_backend.enums.products.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
@Data
public class ProductRequestDTO { @NotBlank
private String name;
    @NotNull
    private ProductCategory category;
    @Positive
    private BigDecimal price;
    @NotNull
    @PositiveOrZero
    private Integer stockQuantity;
    @NotBlank
    private String description;
    @URL
    private String imageUrl;
    @NotNull
    private Long storeId;


}
