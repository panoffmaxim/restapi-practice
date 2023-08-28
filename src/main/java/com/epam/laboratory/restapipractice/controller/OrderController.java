package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.customannotations.LogInvocation;
import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    @LogInvocation
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderRequestDto orderRequestDto,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage,
            @RequestHeader(value = "Accept-Timezone", required = false) String acceptTimezone) {
        try {
            OrderResponseDto createdOrder = orderService.createOrder(orderRequestDto, acceptLanguage, acceptTimezone);
            return new ResponseEntity<>(createdOrder, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error creating order", e);
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
            LOGGER.error("Error completing order", e);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Заказы", description = "Возвращает список заказов")
    @LogInvocation
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        try {
            final List<OrderResponseDto> orderListResponseDto = orderService.getAllOrders();
            return new ResponseEntity<>(orderListResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error getting all orders", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
