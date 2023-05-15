package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<OrderEntity, Long> {
//    OrderEntity saveOrder(OrderEntity order);
//    OrderEntity findOrderById(Long id);
//    List<OrderEntity> findAllOrders();
}
