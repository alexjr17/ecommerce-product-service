package com.ecommerce.product_service.application.service;

import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Order;
import com.ecommerce.product_service.domain.port.in.OrderUseCase;
import com.ecommerce.product_service.domain.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public Mono<Order> createOrder(Order order) {
        return orderRepository.create(order);
    }

    @Override
    public Mono<Order> getOrderById(String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Order not found with id: " + id)));
    }

    @Override
    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Mono<Order> updateOrder(String id, Order order) {
        return orderRepository.update(id, order);
    }

    @Override
    public Mono<Void> deleteOrder(String id) {
        return orderRepository.deleteById(id);
    }
}