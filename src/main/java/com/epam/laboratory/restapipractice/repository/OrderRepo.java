package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.entity.OrderEntity;

import java.util.List;

public interface OrderRepo {
    OrderEntity saveOrder(OrderEntity order);
    OrderEntity findOrderById(Long id);
    List<OrderEntity> findAllOrders();
}
