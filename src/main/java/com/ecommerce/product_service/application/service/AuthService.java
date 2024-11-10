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
    @Override
    public Mono<AuthResponseDTO> authenticate(AuthDTO authDTO) {
        return null;
    }

    @Override
    public Mono<AuthResponseDTO> register(RegisterDTO registerDTO) {
        return null;
    }

    @Override
    public Mono<Boolean> validateToken(String token) {
        return null;
    }

    @Override
    public Mono<String> getUserEmailFromToken(String token) {
        return null;
    }
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    @Value("${jwt.secret-key}")
//    private String secretKey;
//
//    @Value("${jwt.token-validity}")
//    private long validityInMilliseconds;
//
//    @Override
//    public Mono<AuthResponseDTO> authenticate(AuthDTO dto) {
//        return userRepository.userByEmail(dto.getEmail())
//                .filter(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword()))
//                .map(user -> AuthResponseDTO.builder()
//                        .id(user.getId())
//                        .name(user.getUsername())
//                        .email(user.getEmail())
//                        .role("1")
//                        .token(createToken(user.getEmail()))
//                        .build())
//                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
//    }
//
//    @Override
//    public Mono<AuthResponseDTO> register(RegisterDTO registerDTO) {
//        // Verificar si el email ya existe
//        return userRepository.existsByEmail(registerDTO.getEmail())
//                .flatMap(exists -> {
//                    if (exists) {
//                        return Mono.error(new BusinessException("El email ya está registrado"));
//                    }
//
//                    // Crear nuevo usuario
//                    User newUser = User.builder()
//                            .username(registerDTO.getUsername())
//                            .email(registerDTO.getEmail())
//                            .password(passwordEncoder.encode(registerDTO.getPassword()))
////                            .role("USER")
//                            .createdAt(LocalDateTime.now())
//                            .updatedAt(LocalDateTime.now())
//                            .build();
//
//                    return userRepository.save(newUser)
//                            .onErrorResume(ex -> {
//                                return Mono.error(new BusinessException("No se pudo registrar el usuario"));
//                            })
//                            .map(savedUser -> AuthResponseDTO.builder()
//                                    .id(savedUser.getId())
//                                    .name(savedUser.getUsername())
//                                    .email(savedUser.getEmail())
//                                    .role("1")
//                                    .token(createToken(savedUser.getEmail()))
//                                    .build());
//                });
//    }
//    @Override
//    public Mono<Boolean> validateToken(String token) {
//        return Mono.fromCallable(() -> {
//            try {
//                Jwts.parser()
//                        .setSigningKey(secretKey)
//                        .parseClaimsJws(token);
//
//                // Verificar si el token ha expirado
//                String email = getUsernameFromToken(token);
//                log.debug("Token válido para el usuario: {}", email);
//                return true;
//            } catch (ExpiredJwtException e) {
//                log.error("Token expirado: {}", e.getMessage());
//                throw new ExpiredJwtException(null, null, "Token ha expirado");
//            } catch (MalformedJwtException e) {
//                log.error("Token malformado: {}", e.getMessage());
//                throw new MalformedJwtException("Token malformado");
//            } catch (SignatureException e) {
//                log.error("Firma del token inválida: {}", e.getMessage());
//                throw new SignatureException("Firma del token inválida");
//            } catch (Exception e) {
//                log.error("Error validando token: {}", e.getMessage());
//                throw new AuthenticationException("Error de autenticación: " + e.getMessage()) {};
//            }
//        }).subscribeOn(Schedulers.boundedElastic());
//    }
//
//    public Mono<String> getUserEmailFromToken(String token) {
//        return Mono.fromCallable(() -> getUsernameFromToken(token))
//                .onErrorMap(e -> new AuthenticationException("Error al extraer email del token: " + e.getMessage()) {});
//    }
//
//    private String getUsernameFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    private String createToken(String email) {
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + validityInMilliseconds);
//
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
}