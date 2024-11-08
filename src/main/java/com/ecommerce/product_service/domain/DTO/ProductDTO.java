package com.ecommerce.product_service.domain.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@Data
public class ProductDTO {

    @NotBlank(message = "Name is mandatory")
    String name;

    @Positive(message = "Price must be greater than zero")
    BigDecimal price;

    String description;

    @Positive(message = "Stock must be greater than zero")
    Integer stock;
}
