package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.dto.OrderListResponseDto;
import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.mapper.OrderMapper;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final ClientRepo clientRepo;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepo orderRepo, ClientRepo clientRepo, OrderMapper orderMapper) {
        this.orderRepo = orderRepo;
        this.clientRepo = clientRepo;
        this.orderMapper = orderMapper;
    }

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, String acceptLanguage, String acceptTimezone) {
        ClientEntity client = clientRepo.findById(orderRequestDto.getClientId()).orElseThrow(() -> new EntityNotFoundException("Client not found"));

        OrderEntity orderEntity = orderMapper.orderToEntity(orderRequestDto);
        orderEntity.setClient(client);

        return orderMapper.orderToDto(orderRepo.save(orderEntity));
    }

    public OrderListResponseDto getAllOrders(String acceptLanguage, String acceptTimezone) {
        List<OrderEntity> orders = new ArrayList<>();
        orderRepo.findAll().forEach(orders::add);
        return orderMapper.orderToListResponse(orders);
    }

    public OrderResponseDto completeOrder(Long id) {
        OrderEntity orderEntity = orderRepo.findById(id).orElseThrow();
        orderEntity.setCompleted(true);
        return orderMapper.orderToDto(orderRepo.save(orderEntity));
    }
}
