package com.ecommerce.product_service.domain.DTO;

import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.infrastructure.adapter.out.persistence.ProductEntity;

public class ProductMapper {

    public static Product mapToProductDomain(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .active(productEntity.isActive())
                .build();
    }
}
