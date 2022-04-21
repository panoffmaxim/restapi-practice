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
    public ResponseEntity createTodo(@RequestBody OrderEntity todo,
                                     @RequestParam Long clientId) {
        try {
            return ResponseEntity.ok(orderService.createOrder(todo, clientId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping
    public ResponseEntity competeTodo(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(orderService.completedOrder(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}