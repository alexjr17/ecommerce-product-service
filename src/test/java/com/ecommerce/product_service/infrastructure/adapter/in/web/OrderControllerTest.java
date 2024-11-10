//package com.ecommerce.product_service.infrastructure.adapter.in.web;
//
//import com.ecommerce.product_service.domain.DTO.OrderDTO;
//import com.ecommerce.product_service.domain.exception.NotFoundException;
//import com.ecommerce.product_service.domain.model.Order;
//import com.ecommerce.product_service.domain.port.in.OrderUseCase;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.math.BigDecimal;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.times;
//
//@WebFluxTest
//@ContextConfiguration(classes = {OrderController.class})
//@AutoConfigureWebTestClient
//class OrderControllerTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private OrderUseCase orderUseCase;
//
//    private Order testOrder;
//    private OrderDTO testOrderDTO;
//
//    @BeforeEach
//    void setUp() {
//        testOrder = Order.builder()
//                .id(1L)
//                .userId(1L)
//                .status("PENDING")
//                .total(new BigDecimal("990.99"))
//                .build();
//
//        testOrderDTO = OrderDTO.builder()
//                .id(1L)
//                .userId(1L)
//                .status("PENDING")
//                .total(new BigDecimal("990.99"))
//                .build();
//    }
//
//    @Test
//    void createOrder_Success() {
//        when(orderUseCase.createOrder(any(Order.class)))
//                .thenReturn(Mono.just(testOrderDTO));
//
//        webTestClient.post()
//                .uri("/api/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(testOrder)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(OrderDTO.class)
//                .isEqualTo(testOrderDTO);
//
//        verify(orderUseCase, times(1)).createOrder(any(Order.class));
//    }
//
//    @Test
//    void getOrderById_Success() {
//        when(orderUseCase.getOrderById(1L))
//                .thenReturn(Mono.just(testOrderDTO));
//
//        webTestClient.get()
//                .uri("/api/orders/1")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(OrderDTO.class)
//                .isEqualTo(testOrderDTO);
//
//        verify(orderUseCase, times(1)).getOrderById(1L);
//    }
//
//    @Test
//    void getOrderById_NotFound() {
//        when(orderUseCase.getOrderById(999L))
//                .thenReturn(Mono.error(new NotFoundException("Order not found with id: 999")));
//
//        webTestClient.get()
//                .uri("/api/orders/999")
//                .exchange()
//                .expectStatus().isNotFound();
//
//        verify(orderUseCase, times(1)).getOrderById(999L);
//    }
//
//    @Test
//    void getAllOrders_Success() {
//        when(orderUseCase.getAllOrders())
//                .thenReturn(Flux.just(testOrderDTO));
//
//        webTestClient.get()
//                .uri("/api/orders")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(OrderDTO.class)
//                .contains(testOrderDTO);
//
//        verify(orderUseCase, times(1)).getAllOrders();
//    }
//
//    @Test
//    void updateOrder_Success() {
//        Order updatedOrder = Order.builder()
//                .id(1L)
//                .userId(1L)
//                .status("COMPLETED")
//                .total(new BigDecimal("990.99"))
//                .build();
//
//        when(orderUseCase.updateOrder(anyLong(), any(Order.class)))
//                .thenReturn(Mono.just(updatedOrder));
//
//        webTestClient.put()
//                .uri("/api/orders/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(updatedOrder)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Order.class)
//                .isEqualTo(updatedOrder);
//
//        verify(orderUseCase, times(1)).updateOrder(anyLong(), any(Order.class));
//    }
//
//    @Test
//    void updateOrder_NotFound() {
//        Order updateOrder = Order.builder()
//                .id(999L)
//                .userId(1L)
//                .status("COMPLETED")
//                .total(new BigDecimal("990.99"))
//                .build();
//
//        when(orderUseCase.updateOrder(anyLong(), any(Order.class)))
//                .thenReturn(Mono.error(new NotFoundException("Order not found with id: 999")));
//
//        webTestClient.put()
//                .uri("/api/orders/999")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(updateOrder)
//                .exchange()
//                .expectStatus().isNotFound();
//
//        verify(orderUseCase, times(1)).updateOrder(anyLong(), any(Order.class));
//    }
//
//    @Test
//    void deleteOrder_Success() {
//        when(orderUseCase.deleteOrder(1L))
//                .thenReturn(Mono.empty());
//
//        webTestClient.delete()
//                .uri("/api/orders/1")
//                .exchange()
//                .expectStatus().isNoContent();
//
//        verify(orderUseCase, times(1)).deleteOrder(1L);
//    }
//
//    @Test
//    void deleteOrder_NotFound() {
//        when(orderUseCase.deleteOrder(999L))
//                .thenReturn(Mono.error(new NotFoundException("Order not found with id: 999")));
//
//        webTestClient.delete()
//                .uri("/api/orders/999")
//                .exchange()
//                .expectStatus().isNotFound();
//
//        verify(orderUseCase, times(1)).deleteOrder(999L);
//    }
//}