//package com.ecommerce.product_service.application.service;
//
//import com.ecommerce.product_service.domain.DTO.OrderDTO;
//import com.ecommerce.product_service.domain.exception.NotFoundException;
//import com.ecommerce.product_service.domain.model.Order;
//import com.ecommerce.product_service.domain.port.out.OrderRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//import java.math.BigDecimal;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class OrderServiceTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @InjectMocks
//    private OrderService orderService;
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
//        when(orderRepository.create(any(Order.class)))
//                .thenReturn(Mono.just(testOrderDTO));
//
//        StepVerifier.create(orderService.createOrder(testOrder))
//                .expectNext(testOrderDTO)
//                .verifyComplete();
//
//        verify(orderRepository, times(1)).create(any(Order.class));
//    }
//
//    @Test
//    void getOrderById_Success() {
//        when(orderRepository.findById(1L))
//                .thenReturn(Mono.just(testOrderDTO));
//
//        StepVerifier.create(orderService.getOrderById(1L))
//                .expectNext(testOrderDTO)
//                .verifyComplete();
//
//        verify(orderRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void getOrderById_NotFound() {
//        when(orderRepository.findById(999L))
//                .thenReturn(Mono.empty());
//
//        StepVerifier.create(orderService.getOrderById(999L))
//                .expectError(NotFoundException.class)
//                .verify();
//
//        verify(orderRepository, times(1)).findById(999L);
//    }
//
//    @Test
//    void getAllOrders_Success() {
//        when(orderRepository.findAll())
//                .thenReturn(Flux.just(testOrderDTO));
//
//        StepVerifier.create(orderService.getAllOrders())
//                .expectNext(testOrderDTO)
//                .verifyComplete();
//
//        verify(orderRepository, times(1)).findAll();
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
//        when(orderRepository.update(any(Long.class), any(Order.class)))
//                .thenReturn(Mono.just(updatedOrder));
//
//        StepVerifier.create(orderService.updateOrder(1L, updatedOrder))
//                .expectNext(updatedOrder)
//                .verifyComplete();
//
//        verify(orderRepository, times(1)).update(any(Long.class), any(Order.class));
//    }
//
//    @Test
//    void updateOrder_NotFound() {
//        Order updateOrder = Order.builder()
//                .id(999L)
//                .userId(1L)
//                .status("PENDING")
//                .total(new BigDecimal("100.00"))
//                .build();
//
//        when(orderRepository.update(any(Long.class), any(Order.class)))
//                .thenReturn(Mono.error(new NotFoundException("Order not found with id: 999")));
//
//        StepVerifier.create(orderService.updateOrder(999L, updateOrder))
//                .expectError(NotFoundException.class)
//                .verify();
//
//        verify(orderRepository, times(1)).update(any(Long.class), any(Order.class));
//    }
//
//    @Test
//    void deleteOrder_Success() {
//        when(orderRepository.deleteById(1L))
//                .thenReturn(Mono.empty());
//
//        StepVerifier.create(orderService.deleteOrder(1L))
//                .verifyComplete();
//
//        verify(orderRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void deleteOrder_NotFound() {
//        when(orderRepository.deleteById(999L))
//                .thenReturn(Mono.error(new NotFoundException("Order not found with id: 999")));
//
//        StepVerifier.create(orderService.deleteOrder(999L))
//                .expectError(NotFoundException.class)
//                .verify();
//
//        verify(orderRepository, times(1)).deleteById(999L);
//    }
//
//    @Test
//    void createOrder_WithNegativeTotal() {
//        Order invalidOrder = Order.builder()
//                .id(1L)
//                .userId(1L)
//                .status("PENDING")
//                .total(new BigDecimal("-100.00"))
//                .build();
//
//        when(orderRepository.create(any(Order.class)))
//                .thenReturn(Mono.error(new IllegalArgumentException("Total cannot be negative")));
//
//        StepVerifier.create(orderService.createOrder(invalidOrder))
//                .expectError(IllegalArgumentException.class)
//                .verify();
//
//        verify(orderRepository, times(1)).create(any(Order.class));
//    }
//}