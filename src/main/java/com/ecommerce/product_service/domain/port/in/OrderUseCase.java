package com.ecommerce.product_service.domain.port.in;

import com.ecommerce.product_service.domain.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderUseCase {
    Mono<Order> createOrder(Order order);
    Mono<Order> getOrderById(String id);
    Flux<Order> getAllOrders();
    Mono<Order> updateOrder(String id, Order order);
    Mono<Void> deleteOrder(String id);
}