package com.ecommerce.product_service.infrastructure.adapter.in.web;

import com.ecommerce.product_service.domain.DTO.ProductDTO;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.port.in.ProductUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@Valid @RequestBody ProductDTO Dto) {
        return productUseCase.createProduct(Dto);
    }

    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable Long id) {
        return productUseCase.getProductById(id);
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productUseCase.getAllProducts();
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO Dto) {
        return productUseCase.updateProduct(id, Dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productUseCase.deleteProduct(id);
    }
}