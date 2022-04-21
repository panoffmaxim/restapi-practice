package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.model.Order;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ClientRepo clientRepo;

    public Order createOrder(OrderEntity order, Long clientId) {
        ClientEntity client = clientRepo.findById(clientId).get();
        order.setClient(client);
        return Order.toModel(orderRepo.save(order));
    }

    public Order completedOrder(Long id) {
        OrderEntity todo = orderRepo.findById(id).get();
        todo.setCompleted(!todo.getCompleted());
        return Order.toModel(orderRepo.save(todo));
    }
}
