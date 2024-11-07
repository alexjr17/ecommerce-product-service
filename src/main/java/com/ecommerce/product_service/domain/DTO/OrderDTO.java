package com.ecommerce.product_service.domain.DTO;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class OrderDTO {
    Long id;
    Long userId;
    Long productId;
    int quantity;
    BigDecimal total;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    UserDTO user;  // Información del usuario relacionado
    ProductDTO product;  // Información del producto relacionado
}
