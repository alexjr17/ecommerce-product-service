package com.ecommerce.product_service.application.service;

import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.port.out.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id("1")
                .name("Test Product")
                .price(new BigDecimal("990.99"))
                .description("Test Description")
                .stock(10)
                .build();
    }

    @Test
    void createProduct_Success() {
        when(productRepository.create(any(Product.class)))
                .thenReturn(Mono.just(testProduct));

        StepVerifier.create(productService.createProduct(testProduct))
                .expectNext(testProduct)
                .verifyComplete();

        verify(productRepository, times(1)).create(any(Product.class));
    }

    @Test
    void getProductById_Success() {
        when(productRepository.findById("1"))
                .thenReturn(Mono.just(testProduct));

        StepVerifier.create(productService.getProductById("1"))
                .expectNext(testProduct)
                .verifyComplete();

        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById("999"))
                .thenReturn(Mono.empty());

        StepVerifier.create(productService.getProductById("999"))
                .expectError(ProductNotFoundException.class)
                .verify();

        verify(productRepository, times(1)).findById("999");
    }

    @Test
    void getAllProducts_Success() {
        when(productRepository.findAll())
                .thenReturn(Flux.just(testProduct));

        StepVerifier.create(productService.getAllProducts())
                .expectNext(testProduct)
                .verifyComplete();

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void updateProduct_Success() {
        Product updatedProduct = Product.builder()
                .id("1")
                .name("Updated Product")
                .price(new BigDecimal("1990.99"))
                .description("Updated Description")
                .stock(20)
                .build();

        when(productRepository.update(anyString(), any(Product.class)))
                .thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(productService.updateProduct("1", updatedProduct))
                .expectNext(updatedProduct)
                .verifyComplete();

        verify(productRepository, times(1)).update(anyString(), any(Product.class));
    }

    @Test
    void updateProduct_NotFound() {
        Product updateProduct = Product.builder()
                .id("999")
                .name("Non Existent Product")
                .price(new BigDecimal("100.00"))
                .description("Test")
                .stock(1)
                .build();

        when(productRepository.update(anyString(), any(Product.class)))
                .thenReturn(Mono.error(new ProductNotFoundException("Product not found with id: 999")));

        StepVerifier.create(productService.updateProduct("999", updateProduct))
                .expectError(ProductNotFoundException.class)
                .verify();

        verify(productRepository, times(1)).update(anyString(), any(Product.class));
    }

    @Test
    void deleteProduct_Success() {
        when(productRepository.deleteById("1"))
                .thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct("1"))
                .verifyComplete();

        verify(productRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteProduct_NotFound() {
        when(productRepository.deleteById("999"))
                .thenReturn(Mono.error(new ProductNotFoundException("Product not found with id: 999")));

        StepVerifier.create(productService.deleteProduct("999"))
                .expectError(ProductNotFoundException.class)
                .verify();

        verify(productRepository, times(1)).deleteById("999");
    }

    @Test
    void createProduct_WithNegativePrice() {
        Product invalidProduct = Product.builder()
                .id("1")
                .name("Invalid Product")
                .price(new BigDecimal("-100.00"))
                .description("Test Description")
                .stock(10)
                .build();

        when(productRepository.create(any(Product.class)))
                .thenReturn(Mono.error(new IllegalArgumentException("Price cannot be negative")));

        StepVerifier.create(productService.createProduct(invalidProduct))
                .expectError(IllegalArgumentException.class)
                .verify();
    }
}