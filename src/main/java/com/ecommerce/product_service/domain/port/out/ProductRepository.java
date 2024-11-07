package com.ecommerce.product_service.domain.port.out;

import com.ecommerce.product_service.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> create(Product product);
    Mono<Product> update(String id, Product product);
    Mono<Product> findById(String id);
    Flux<Product> findAll();
    Mono<Void> deleteById(String id);
    Mono<Boolean> existsById(String id);
}