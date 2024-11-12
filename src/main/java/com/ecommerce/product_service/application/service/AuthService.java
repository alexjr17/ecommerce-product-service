package com.ecommerce.product_service.application.service;

import com.ecommerce.product_service.domain.DTO.AuthDTO;
import com.ecommerce.product_service.domain.DTO.AuthResponseDTO;
import com.ecommerce.product_service.domain.DTO.RegisterDTO;
import com.ecommerce.product_service.domain.model.User;
import com.ecommerce.product_service.domain.port.in.AuthUseCase;
import com.ecommerce.product_service.domain.port.out.UserRepository;
import com.ecommerce.product_service.infrastructure.exception.AuthenticationException;
import com.ecommerce.product_service.infrastructure.exception.BusinessException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.Date;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public Mono<AuthResponseDTO> authenticate(AuthDTO dto) {
        return userRepository.userByEmail(dto.getEmail())
                .filter(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                .map(user -> AuthResponseDTO.builder()
                        .id(user.getId())
                        .name(user.getUsername())
                        .email(user.getEmail())
                        .role("1")
                        .token(jwtService.generateToken(user.getEmail()))
                        .build())
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }

    @Override
    public Mono<AuthResponseDTO> register(RegisterDTO registerDTO) {
        return userRepository.existsByEmail(registerDTO.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BusinessException("El email ya está registrado"));
                    }

                    User newUser = User.builder()
                            .username(registerDTO.getUsername())
                            .email(registerDTO.getEmail())
                            .password(passwordEncoder.encode(registerDTO.getPassword()))
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();

                    return userRepository.save(newUser)
                            .onErrorResume(ex ->
                                    Mono.error(new BusinessException("No se pudo registrar el usuario"))
                            )
                            .map(savedUser -> AuthResponseDTO.builder()
                                    .id(savedUser.getId())
                                    .name(savedUser.getUsername())
                                    .email(savedUser.getEmail())
                                    .role("1")
                                    .token(jwtService.generateToken(savedUser.getEmail()))
                                    .build());
                });
    }
}