package com.ecommerce.product_service.infrastructure.adapter.out.persistence;

import com.ecommerce.product_service.domain.DTO.OrderDTO;
import com.ecommerce.product_service.domain.DTO.ProductDTO;
import com.ecommerce.product_service.domain.DTO.UserDTO;
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
    public Mono<OrderDTO> create(Order order) {
        OrderEntity entity = mapToEntity(order);
        return template.insert(entity)
                .flatMap(this::mapToDomainDTO);
    }

    @Override
    public Mono<Order> update(Long id, Order order) {
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
                            .then(Mono.just(mapToDomain(existingEntity)));
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found with id: " + id)));
    }

    @Override
    public Mono<OrderDTO> findById(Long id) {
        return template.selectOne(
                        Query.query(where("id").is(id)),
                        OrderEntity.class
                )
                .flatMap(this::mapToDomainDTO)
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found with id: " + id)));
    }

    @Override
    public Flux<OrderDTO> findAll() {
        return template.select(OrderEntity.class)
                .all()
                .flatMap(this::mapToDomainDTO);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return template.delete(
                Query.query(where("id").is(id)),
                OrderEntity.class
        ).then();
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
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
    private Mono<OrderDTO> mapToDomainDTO(OrderEntity entity) {
        return Mono.zip(
                        template.selectOne(Query.query(where("id").is(entity.getUserId())), UserEntity.class),
                        template.selectOne(Query.query(where("id").is(entity.getProductId())), ProductEntity.class)
                )
                .flatMap(tuple -> {
                    UserEntity user = tuple.getT1();
                    ProductEntity product = tuple.getT2();

                    return Mono.just(OrderDTO.builder()
                            .id(entity.getId())
                            .userId(entity.getUserId())
                            .productId(entity.getProductId())
                            .user(UserDTO.builder()
                                    .id(user.getId())
                                    .username(user.getUsername())
                                    .email(user.getEmail())
                                    .build())
                            .product(ProductDTO.builder()
                                    .id(product.getId())
                                    .name(product.getName())
                                    .price(product.getPrice())
                                    .build())
                            .quantity(entity.getQuantity())
                            .total(entity.getTotal())
                            .status(entity.getStatus())
                            .createdAt(entity.getCreatedAt())
                            .updatedAt(entity.getUpdatedAt())
                            .build());
                });
    }
}
