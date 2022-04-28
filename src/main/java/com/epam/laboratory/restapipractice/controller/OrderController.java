package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @PutMapping
    public ResponseEntity completeOrder(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(orderService.completedOrder(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}