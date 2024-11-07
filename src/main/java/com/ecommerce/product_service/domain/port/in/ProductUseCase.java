package com.ecommerce.product_service.domain.port.in;

import com.ecommerce.product_service.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> createProduct(Product product);
    Mono<Product> getProductById(String id);
    Flux<Product> getAllProducts();
    Mono<Product> updateProduct(String id, Product product);
    Mono<Void> deleteProduct(String id);
}