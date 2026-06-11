package com.flashcart.flashcart_backend.product.mapper;

import com.flashcart.flashcart_backend.product.dto.ProductRequestDTO;
import com.flashcart.flashcart_backend.product.dto.ProductResponseDTO;
import com.flashcart.flashcart_backend.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDTO productRequestDTO) ;
    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeName", source = "store.storeName")
    ProductResponseDTO toResponse(Product product) ;
    void updateProduct(@MappingTarget Product product, ProductRequestDTO productRequestDTO);
}
//@MappingTaregt --Naya object mat banao Isi object ko update karo
//@Mapping --Data kaha se uthana hai  ->kaha rakhna hai