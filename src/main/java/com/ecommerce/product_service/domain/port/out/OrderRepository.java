package com.ecommerce.product_service.domain.port.out;

import com.ecommerce.product_service.domain.DTO.OrderDTO;
import com.ecommerce.product_service.domain.model.Order;
import com.ecommerce.product_service.infrastructure.adapter.out.persistence.OrderEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository {
    Mono<OrderDTO> create(Order order);
    Mono<Order> update(Long id, Order order);
    Mono<OrderDTO> findById(Long id);
    Flux<OrderDTO> findAll();
    Mono<Void> deleteById(Long id);
    Mono<Boolean> existsById(Long id);
}