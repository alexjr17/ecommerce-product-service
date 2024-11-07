package com.ecommerce.product_service.domain.port.out;

import com.ecommerce.product_service.domain.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository {
    Mono<Order> create(Order order);
    Mono<Order> update(String id, Order order);
    Mono<Order> findById(String id);
    Flux<Order> findAll();
    Mono<Void> deleteById(String id);
    Mono<Boolean> existsById(String id);
}