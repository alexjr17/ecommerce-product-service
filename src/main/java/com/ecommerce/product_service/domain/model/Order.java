package com.ecommerce.product_service.domain.model;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class Order {
    Long id;
    Long userId;
    Long productId;
    int quantity;
    BigDecimal total;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

