package com.ecommerce.product_service.application.service;

import com.ecommerce.product_service.domain.DTO.OrderDTO;
import com.ecommerce.product_service.domain.exception.NotFoundException;
import com.ecommerce.product_service.domain.model.Order;
import com.ecommerce.product_service.domain.port.in.OrderUseCase;
import com.ecommerce.product_service.domain.port.out.OrderRepository;
import com.ecommerce.product_service.infrastructure.adapter.out.persistence.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public Mono<OrderDTO> createOrder(Order order) {
        return orderRepository.create(order);
    }

    @Override
    public Mono<OrderDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found with id: " + id)));
    }

    @Override
    public Flux<OrderDTO> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Mono<Order> updateOrder(Long id, Order order) {
        return orderRepository.update(id, order);
    }

    @Override
    public Mono<Void> deleteOrder(Long id) {
        return orderRepository.deleteById(id);
    }
}