//package com.epam.laboratory.restapipractice.service;
//
//import com.epam.laboratory.restapipractice.entity.ClientEntity;
//import com.epam.laboratory.restapipractice.entity.OrderEntity;
//import com.epam.laboratory.restapipractice.model.Order;
//import com.epam.laboratory.restapipractice.repository.ClientRepo;
//import com.epam.laboratory.restapipractice.repository.OrderRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class OrderService {
//
//    @Autowired
//    private OrderRepo orderRepo;
//    @Autowired
//    private ClientRepo clientRepo;
//
//    public Order createOrder(OrderEntity order, Long clientId) {
//        ClientEntity client = clientRepo.findClientById(clientId);
//        order.setClient(client);
//        return Order.toModel(orderRepo.save(order));
//    }
//
//    public List<OrderEntity> findAllOrders() {
//        List<OrderEntity> orders = new ArrayList<>();
//        orderRepo.findAll().forEach(orders::add);
//        return orders;
//    }
//
//    public Order completedOrder(Long id) {
//        OrderEntity order = orderRepo.findById(id).get();
//        order.setCompleted(!order.getCompleted());
//        return Order.toModel(orderRepo.save(order));
//    }
//}
