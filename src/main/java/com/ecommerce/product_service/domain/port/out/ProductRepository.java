package com.ecommerce.product_service.domain.port.out;

import com.ecommerce.product_service.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> create(Product product);
    Mono<Product> update(Long id, Product product);
    Mono<Product> findById(Long id);
    Flux<Product> findAll();
    Mono<Void> deleteById(Long id);
    Mono<Boolean> existsById(Long id);
}