package com.ecommerce.product_service.infrastructure.adapter.in.web;

import com.ecommerce.product_service.domain.DTO.AuthDTO;
import com.ecommerce.product_service.domain.DTO.AuthResponseDTO;
import com.ecommerce.product_service.domain.DTO.RegisterDTO;
import com.ecommerce.product_service.domain.port.in.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;
    @PostMapping("/login")
    public Mono<AuthResponseDTO> loginUser(@Valid @RequestBody AuthDTO dto) {
        return authUseCase.authenticate(dto);
    }

    @PostMapping("/register")
    public Mono<AuthResponseDTO> register(@Valid @RequestBody RegisterDTO dto) {
        return authUseCase.register(dto);
    }
}
