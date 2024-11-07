package com.ecommerce.product_service.domain.DTO;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProductDTO {
    Long id;
    String name;
    String description;
    BigDecimal price;
    Integer stock;

    boolean active;
}
