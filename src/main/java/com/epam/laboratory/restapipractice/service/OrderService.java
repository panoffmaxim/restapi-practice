package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.model.Order;
import com.epam.laboratory.restapipractice.repository.impl.ClientRepoImpl;
import com.epam.laboratory.restapipractice.repository.impl.OrderRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepoImpl orderRepoImpl;
    @Autowired
    private ClientRepoImpl clientRepoImpl;

    public Order createOrder(OrderEntity order, Long clientId) {
        ClientEntity client = clientRepoImpl.findClientById(clientId);
        order.setClient(client);
        return Order.toModel(orderRepoImpl.saveOrder(order));
    }

    public Order completedOrder(Long id) {
        OrderEntity order = orderRepoImpl.findOrderById(id);
        order.setCompleted(!order.getCompleted());
        return Order.toModel(orderRepoImpl.saveOrder(order));
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepoImpl.findAllOrders();
    }
}
