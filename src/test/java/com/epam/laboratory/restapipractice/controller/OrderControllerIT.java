package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
public class OrderControllerIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private OrderRepo orderRepository;
    @Autowired
    private ClientRepo clientRepository;
    @Container
    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");
        MY_SQL_CONTAINER.start();
    }

    @BeforeEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username", () -> MY_SQL_CONTAINER.getUsername());
        registry.add("spring.datasource.password", () -> MY_SQL_CONTAINER.getPassword());
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    @Test
    void testCreateOrder() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setClientName("Miha");
        clientRepository.save(clientEntity);

        OrderRequestDto orderOne = new OrderRequestDto();
        orderOne.setClientId(1L);
        orderOne.setDeliveryInf("order-1");
        orderOne.setPaymentMethod("cash");

        webTestClient.post()
                .uri("/orders/create")
                .bodyValue(orderOne)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isCreated()
                .expectBody(OrderResponseDto.class)
                .value(orderResponseDto -> {
                    assertNotNull(orderResponseDto);
                    assertNotNull(orderResponseDto.getId());
                    assertEquals(orderOne.getClientId(), orderResponseDto.getClientId());
                    assertEquals(orderOne.getDeliveryInf(), orderResponseDto.getDeliveryInf());
                    assertEquals(orderOne.getPaymentMethod(), orderResponseDto.getPaymentMethod());
                    assertNotNull(orderResponseDto.getCreationDateTime());

                    Optional<OrderEntity> savedOrder = orderRepository.findById(orderResponseDto.getId());
                    assertTrue(savedOrder.isPresent());
                    assertEquals(orderOne.getClientId(), savedOrder.get().getClient().getId());
                    assertEquals(orderOne.getDeliveryInf(), savedOrder.get().getDeliveryInf());
                    assertEquals(orderOne.getPaymentMethod(), savedOrder.get().getPaymentMethod());
                });
    }

    @Test
    void testGetAllOrders() {
        OrderEntity orderTwo = new OrderEntity();
        orderTwo.setId(3L);
        orderTwo.setCompleted(true);
        orderTwo.setDeliveryInf("order-2");
        orderTwo.setPaymentMethod("crypto");
        orderTwo.setCreationDateTime(LocalDateTime.now());
        orderRepository.save(orderTwo);

        OrderEntity orderThree = new OrderEntity();
        orderThree.setId(4L);
        orderThree.setCompleted(true);
        orderThree.setDeliveryInf("order-3");
        orderThree.setPaymentMethod("card");
        orderThree.setCreationDateTime(LocalDateTime.now());
        orderRepository.save(orderThree);

        List<OrderEntity> allEntities = new ArrayList<>();
        allEntities.add(orderTwo);
        allEntities.add(orderThree);

        webTestClient.get()
                .uri("/orders/all")
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk()
                .expectBodyList(OrderResponseDto.class)
                .consumeWith(response -> {
                    List<OrderResponseDto> orderResponseDtos = response.getResponseBody();
                    assertNotNull(orderResponseDtos);
                    assertEquals(allEntities.size(), orderResponseDtos.size());
                });
    }
}
