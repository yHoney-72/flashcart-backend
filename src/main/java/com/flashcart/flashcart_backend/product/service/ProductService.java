package com.flashcart.flashcart_backend.product.service;

import com.flashcart.flashcart_backend.enums.store.StoreStatus;
import com.flashcart.flashcart_backend.exception.ResourceNotFoundException;
import com.flashcart.flashcart_backend.product.dto.ProductRequestDTO;
import com.flashcart.flashcart_backend.product.dto.ProductResponseDTO;
import com.flashcart.flashcart_backend.product.entity.Product;
import com.flashcart.flashcart_backend.product.mapper.ProductMapper;
import com.flashcart.flashcart_backend.product.repository.ProductRepository;
import com.flashcart.flashcart_backend.store.entity.Store;
import com.flashcart.flashcart_backend.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.flashcart.flashcart_backend.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
   private final ProductRepository productRepository;
   private final  ProductMapper productMapper;
    private final StoreRepository storeRepository;
   public ProductService (ProductRepository productRepository , ProductMapper productMapper, StoreRepository storeRepository) {
       this.productRepository = productRepository;
       this.productMapper = productMapper;
       this.storeRepository = storeRepository;
   }
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO){
        Store store = storeRepository.findByIdAndIsDeletedFalse(productRequestDTO.getStoreId()) .orElseThrow(() -> new ResourceNotFoundException( "Store not found with id: " + productRequestDTO.getStoreId()));
        validateStoreOwnership(store);
        if(store.getStatus() != StoreStatus.APPROVED){
            throw new AccessDeniedException(
                    "Store is not approved yet");
        }
        Product product = productMapper.toEntity(productRequestDTO);
        product.setStore(store);
        product.setIsAvailable( productRequestDTO.getStockQuantity() > 0);
        product.setSku(generateSku(productRequestDTO));
        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    private String generateSku(ProductRequestDTO productRequestDTO) {
       String namePart = productRequestDTO.getName()
               .replaceAll("\\s+","")
               .substring(0,2)
               .toUpperCase();
       String lastPart = productRequestDTO.getCategory()
               .name()
               .substring(0,2)
               .toUpperCase();
       long time = System.currentTimeMillis()%10000;
       return namePart+"-" + lastPart +"-"+ time;
    }

    public ProductResponseDTO findById(Long id){
        Product product = productRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException( "Product not found with id: " + id));
        return productMapper.toResponse(product);
    }
public List<ProductResponseDTO> findAll(){
       return productRepository.findByIsDeletedFalse()
               .stream()
               .map(productMapper::toResponse)
               .toList();
}
public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO){
       Product product =productRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()-> new ResourceNotFoundException("Product not found with id: " + id));
    validateProductOwnership(product);
      //Yaha agar product na mile to custom exception throw karte hain.
       // Ye service sirf problem batati hai, response nahi banati.
       // GlobalExceptionHandler is exception ko pakad ke proper HTTP response (404 + message) client ko bhejta hai.
                       productMapper.updateProduct(product, productRequestDTO);
      Product savedProduct = productRepository.save(product);
      return productMapper.toResponse(savedProduct);
}
    public void deleteProduct(Long id){

        Product product = productRepository.findByIdAndIsDeletedFalse(id) .orElseThrow(() -> new ResourceNotFoundException(   "Product not found with id: " + id));
        validateProductOwnership(product);
        product.setIsDeleted(true);
        product.setDeletedAt(LocalDateTime.now());

        productRepository.save(product);
    }
    private void validateProductOwnership(Product product) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (!product.getStore().getOwner()
                .getId()
                .equals(currentUser.getId())) {throw new AccessDeniedException("You are not allowed to access this product");
        }
    }
    private void validateStoreOwnership(Store store) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (!store.getOwner()
                .getId()
                .equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    "You are not allowed to access this store");
        }
    }

}
