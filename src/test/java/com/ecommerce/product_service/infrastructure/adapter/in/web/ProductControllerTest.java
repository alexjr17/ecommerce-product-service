package com.ecommerce.product_service.infrastructure.adapter.in.web;

import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.port.in.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@WebFluxTest
@ContextConfiguration(classes = {ProductController.class})
@AutoConfigureWebTestClient
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductUseCase productUseCase;

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
        when(productUseCase.createProduct(any(Product.class)))
                .thenReturn(Mono.just(testProduct));

        webTestClient.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testProduct)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .isEqualTo(testProduct);

        verify(productUseCase, times(1)).createProduct(any(Product.class));
    }

    @Test
    void getProductById_Success() {
        when(productUseCase.getProductById("1"))
                .thenReturn(Mono.just(testProduct));

        webTestClient.get()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(testProduct);

        verify(productUseCase, times(1)).getProductById("1");
    }

    @Test
    void getProductById_NotFound() {
        when(productUseCase.getProductById("999"))
                .thenReturn(Mono.error(new ProductNotFoundException("Product not found with id: 999")));

        webTestClient.get()
                .uri("/api/products/999")
                .exchange()
                .expectStatus().isNotFound();

        verify(productUseCase, times(1)).getProductById("999");
    }

    @Test
    void getAllProducts_Success() {
        when(productUseCase.getAllProducts())
                .thenReturn(Flux.just(testProduct));

        webTestClient.get()
                .uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(testProduct);

        verify(productUseCase, times(1)).getAllProducts();
    }

    @Test
    void updateProduct_Success() {
        Product updatedProduct = Product.builder()
                .id("1")
                .name("Updated Product")
                .price(new BigDecimal("990.99"))
                .description("Updated Description")
                .stock(20)
                .build();

        when(productUseCase.updateProduct(anyString(), any(Product.class)))
                .thenReturn(Mono.just(updatedProduct));

        webTestClient.put()
                .uri("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedProduct)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(updatedProduct);

        verify(productUseCase, times(1)).updateProduct(anyString(), any(Product.class));
    }

    @Test
    void deleteProduct_Success() {
        when(productUseCase.deleteProduct("1"))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isNoContent();

        verify(productUseCase, times(1)).deleteProduct("1");
    }

    @Test
    void createProduct_BadRequest() {
        Product invalidProduct = Product.builder()
                .price(new BigDecimal("-100")) // Precio negativo inválido
                .build();

        webTestClient.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidProduct)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateProduct_NotFound() {
        Product updateProduct = Product.builder()
                .id("999")
                .name("Non Existent Product")
                .price(new BigDecimal("100.0"))
                .description("Test")
                .stock(1)
                .build();

        when(productUseCase.updateProduct(anyString(), any(Product.class)))
                .thenReturn(Mono.error(new ProductNotFoundException("Product not found with id: 999")));

        webTestClient.put()
                .uri("/api/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateProduct)
                .exchange()
                .expectStatus().isNotFound();
    }
}