package com.ecommerce.product_service.infrastructure.adapter.in.web;

import com.ecommerce.product_service.domain.DTO.OrderDTO;
import com.ecommerce.product_service.domain.model.Order;
import com.ecommerce.product_service.domain.port.in.OrderUseCase;
import com.ecommerce.product_service.infrastructure.adapter.out.persistence.OrderEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderDTO> createOrder(@Valid @RequestBody Order order) {
        return orderUseCase.createOrder(order);
    }

    @GetMapping("/{id}")
    public Mono<OrderDTO> getOrderById(@PathVariable Long id) {
        return orderUseCase.getOrderById(id);
    }

    @GetMapping
    public Flux<OrderDTO> getAllOrders() {
        return orderUseCase.getAllOrders();
    }

    @PutMapping("/{id}")
    public Mono<Order> updateOrder(@PathVariable Long id,@Valid @RequestBody Order order) {
        return orderUseCase.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteOrder(@PathVariable Long id) {
        return orderUseCase.deleteOrder(id);
    }
}