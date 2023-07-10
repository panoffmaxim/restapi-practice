package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.mapper.OrderMapper;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import com.epam.laboratory.restapipractice.response.OrderListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private OrderMapper orderMapper;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long clientId) {
        ClientEntity client = clientRepo.findById(clientId).orElseThrow();
        OrderEntity orderEntity = orderMapper.orderToEntity(orderRequestDto);
        orderEntity.setClient(client);
        return orderMapper.orderToDto(orderRepo.save(orderEntity));
    }

    public OrderListResponse getAllOrders() {
        List<OrderEntity> orders = new ArrayList<>();
        orderRepo.findAll().forEach(orders::add);
        OrderListResponse orderListResponse = orderMapper.orderToListResponse(orders);
        return orderListResponse;
    }

    public OrderResponseDto completeOrder(Long id) {
        OrderEntity orderEntity = orderRepo.findById(id).orElseThrow();
        orderEntity.setCompleted(true);
        return orderMapper.orderToDto(orderRepo.save(orderEntity));
    }
}
