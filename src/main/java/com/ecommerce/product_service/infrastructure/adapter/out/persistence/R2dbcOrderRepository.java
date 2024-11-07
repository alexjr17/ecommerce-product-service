package com.ecommerce.product_service.infrastructure.adapter.out.persistence;

import com.ecommerce.product_service.domain.exception.DuplicateException;
import com.ecommerce.product_service.domain.exception.NotFoundException;
import com.ecommerce.product_service.domain.model.Order;
import com.ecommerce.product_service.domain.port.out.OrderRepository;
import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class R2dbcOrderRepository implements OrderRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Order> create(Order order) {
        return existsById(order.getId())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DuplicateException("Order with ID " + order.getId() + " already exists"));
                    } else {
                        return saveOrder(order);
                    }
                })
                .onErrorMap(R2dbcDataIntegrityViolationException.class, ex ->
                        new RuntimeException("Data integrity violation: " + ex.getMessage()));
    }

    private Mono<Order> saveOrder(Order order) {
        OrderEntity entity = mapToEntity(order);
        return template.insert(entity)
                .map(this::mapToDomain);
    }

    @Override
    public Mono<Order> update(String id, Order order) {
        return template.selectOne(
                        Query.query(where("id").is(id)),
                        OrderEntity.class
                )
                .flatMap(existingEntity -> {
                    existingEntity.setUserId(order.getUserId());
                    existingEntity.setProductId(order.getProductId());
                    existingEntity.setQuantity(order.getQuantity());
                    existingEntity.setTotal(order.getTotal());
                    existingEntity.setStatus(order.getStatus());
                    return template.update(existingEntity)
                            .map(this::mapToDomain);
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found with id: " + id)));
    }

    @Override
    public Mono<Order> findById(String id) {
        return template.selectOne(
                Query.query(where("id").is(id)),
                OrderEntity.class
        ).map(this::mapToDomain).switchIfEmpty(Mono.error(new NotFoundException("Order not found with id: " + id)));
    }

    @Override
    public Flux<Order> findAll() {
        return template.select(OrderEntity.class)
                .all()
                .map(this::mapToDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return template.delete(
                Query.query(where("id").is(id)),
                OrderEntity.class
        ).then();
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return template.exists(
                Query.query(where("id").is(id)),
                OrderEntity.class
        );
    }

    private OrderEntity mapToEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .total(order.getTotal())
                .status(order.getStatus())
                .build();
    }

    private Order mapToDomain(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .total(entity.getTotal())
                .status(entity.getStatus())
                .build();
    }
}