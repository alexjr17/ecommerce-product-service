package com.ecommerce.product_service.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
//    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
