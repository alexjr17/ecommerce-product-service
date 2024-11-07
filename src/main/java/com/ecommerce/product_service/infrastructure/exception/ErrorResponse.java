package com.ecommerce.product_service.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class ErrorResponse {
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp = LocalDateTime.now();

    int status;
    String message;

    @Builder.Default
    String path = "";

    @Builder.Default
    List<String> errors = List.of();
}