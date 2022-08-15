package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.response.OrderListResponse;
import com.epam.laboratory.restapipractice.response.OrderResponse;
import com.epam.laboratory.restapipractice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderEntity order,
                                      @RequestParam Long clientId) {
        try {
            return ResponseEntity.ok(orderService.createOrder(order, clientId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping
    public ResponseEntity completeOrder(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(orderService.completedOrder(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

//    @GetMapping
//    public ResponseEntity getAllOrders() {
//        return ResponseEntity.ok(orderService.getAllOrders());
//    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Заказы", description = "Возвращает список заказов")
    public ResponseEntity<OrderListResponse> getAllOrders() {
        final List<OrderEntity> orders = orderService.getAllOrders();
        if (orders == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final OrderListResponse orderListResponse = new OrderListResponse(
                orders.stream().map(orderEntity -> new OrderResponse(orderEntity.getId(),
                                orderEntity.getClientName(),
                                orderEntity.getCompleted(),
                                orderEntity.getDeliveryInf(),
                                orderEntity.getClient().getId()))
                        .collect(Collectors.toList())
        );
        return new ResponseEntity<>(orderListResponse, HttpStatus.OK);
    }
}