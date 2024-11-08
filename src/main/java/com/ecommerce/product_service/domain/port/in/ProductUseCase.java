package com.ecommerce.product_service.domain.port.in;

import com.ecommerce.product_service.domain.DTO.ProductDTO;
import com.ecommerce.product_service.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> createProduct(ProductDTO dto);
    Mono<Product> getProductById(Long id);
    Flux<Product> getAllProducts();
    Mono<Product> updateProduct(Long id, ProductDTO dto);
    Mono<Void> deleteProduct(Long id);
}