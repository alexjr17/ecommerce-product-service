package com.ecommerce.product_service.domain.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@Data
public class Product {
    Long id;
    String name;
    String description;
    BigDecimal price;
    Integer stock;
    boolean active;
}