package com.ecommerce.product_service.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@Data
public class User {
    String Id;
    String UserName;
    String Email;
    String Password;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
