package com.ecommerce.product_service.domain.port.in;

import com.ecommerce.product_service.domain.DTO.OrderDTO;
import com.ecommerce.product_service.domain.DTO.OrderResponseDTO;
import com.ecommerce.product_service.domain.model.Order;
import com.ecommerce.product_service.infrastructure.adapter.out.persistence.OrderEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderUseCase {
    Mono<OrderDTO> createOrder(Order order);

    Mono<OrderDTO> getOrderById(Long id);
    Flux<OrderDTO> getAllOrders();
    Mono<Order> updateOrder(Long id, Order order);
    Mono<Void> deleteOrder(Long id);
}