package com.ecommerce.product_service.infrastructure.adapter.out.persistence;

import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static org.springframework.data.relational.core.query.Criteria.where;
@Slf4j

@Repository
@RequiredArgsConstructor
public class R2dbcProductRepository implements ProductRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Product> create(Product product) {
        ProductEntity entity = mapToEntity(product);
        return template.insert(entity)
                .map(this::mapToDomain);
    }

    @Override
    public Mono<Product> update(Long id, Product product) {
        return template.selectOne(
                        Query.query(where("id").is(id)),
                        ProductEntity.class
                )
                .flatMap(existingEntity -> {
                    System.out.println(existingEntity);
                    existingEntity.setName(product.getName());
                    existingEntity.setDescription(product.getDescription());
                    existingEntity.setPrice(product.getPrice());
                    existingEntity.setStock(product.getStock());
                    existingEntity.setActive(product.isActive());
                    return template.update(existingEntity)
                            .map(this::mapToDomain);
                })
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with id: " + id)));
    }

    @Override
    public Mono<Product> findById(Long id) {
        return template.selectOne(
                        Query.query(where("id").is(id)),
                        ProductEntity.class
                ).map(this::mapToDomain)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with id: " + id)));
    }

    @Override
    public Flux<Product> findAll() {
        return template.select(ProductEntity.class)
                .all()
                .map(this::mapToDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return template.delete(
                Query.query(where("id").is(id)),
                ProductEntity.class
        ).then();
    }
    @Override
    public Mono<Boolean> existsById(Long id) {
        return template.exists(
                Query.query(where("id").is(id)),
                ProductEntity.class
        );
    }
    private ProductEntity mapToEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .active(product.isActive())
                .build();
    }

    private Product mapToDomain(ProductEntity entity) {
        log.error("Validation error occurred: {}", entity);
        if (entity == null) {
            throw new IllegalArgumentException("ProductEntity cannot be null");
        }

        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .active(entity.isActive())
                .build();
    }
}