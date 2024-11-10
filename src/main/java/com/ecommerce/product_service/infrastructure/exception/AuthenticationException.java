package com.ecommerce.product_service.infrastructure.exception;
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}