package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.customannotations.LogInvocation;
import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    @LogInvocation
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderRequestDto orderRequestDto,
            @RequestHeader(value = "Accept-Language", defaultValue = "en-US") String acceptLanguage,
            @RequestHeader(value = "Accept-Timezone", defaultValue = "UTC") String acceptTimezone) {
        try {
            OrderResponseDto createdOrder = orderService.createOrder(orderRequestDto, acceptLanguage, acceptTimezone);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating order", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @LogInvocation
    public ResponseEntity<OrderResponseDto> completeOrder(@RequestParam Long id) {
        try {
            OrderResponseDto completedOrder = orderService.completeOrder(id);
            return new ResponseEntity<>(completedOrder, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error completing order", e);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Заказы", description = "Возвращает список заказов")
    @LogInvocation
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(
            @RequestHeader(value = "Accept-Language", defaultValue = "en-US") String acceptLanguage,
            @RequestHeader(value = "Accept-Timezone", defaultValue = "UTC") String acceptTimezone) {
        try {
            final List<OrderResponseDto> orderListResponseDto = orderService.getAllOrders(acceptLanguage, acceptTimezone);
            return new ResponseEntity<>(orderListResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting all orders", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Нахождение заказа", description = "Возвращает заказ по его ID")
    @LogInvocation
    public ResponseEntity<OrderResponseDto> getOrder(
            @PathVariable(name = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en-US") String acceptLanguage,
            @RequestHeader(value = "Accept-Timezone", defaultValue = "UTC") String acceptTimezone) {
        try {
            log.info("Controller: Fetching order with id {}", id);
            OrderResponseDto orderResponseDto = orderService.getOrder(id, acceptLanguage, acceptTimezone);
            return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error reading order", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
