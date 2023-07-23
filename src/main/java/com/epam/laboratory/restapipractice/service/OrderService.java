package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.mapper.OrderMapper;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import com.epam.laboratory.restapipractice.dto.OrderListResponseDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final ClientRepo clientRepo;
    private final OrderMapper orderMapper;
    private final ZoneId serverZoneId;

    public OrderService(OrderRepo orderRepo, ClientRepo clientRepo, OrderMapper orderMapper) {
        this.orderRepo = orderRepo;
        this.clientRepo = clientRepo;
        this.orderMapper = orderMapper;
        this.serverZoneId = ZoneId.systemDefault();
    }

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long clientId, String acceptLanguage, String acceptTimezone) {
        ClientEntity client = clientRepo.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client not found"));

        ZonedDateTime currentDateTime;
        if (acceptTimezone != null && !acceptTimezone.isEmpty()) {
            ZoneId clientZoneId = ZoneId.of("UTC+0");
            currentDateTime = ZonedDateTime.now(clientZoneId);
        } else {
            currentDateTime = ZonedDateTime.now(serverZoneId);
        }
        LocalDateTime creationDateTime = currentDateTime.toLocalDateTime();

        OrderEntity orderEntity = orderMapper.orderToEntity(orderRequestDto);

        orderEntity.setCreationDateTime(creationDateTime);
        orderEntity.setClient(client);
        OrderResponseDto orderResponseDto = orderMapper.orderToDto(orderRepo.save(orderEntity));
        String responseDateTime = formatLocalDateTime(creationDateTime, acceptLanguage);
        orderResponseDto.setCreationDateTime(responseDateTime);
        return orderResponseDto;
    }

    public OrderListResponseDto getAllOrders() {
        List<OrderEntity> orders = new ArrayList<>();
        orderRepo.findAll().forEach(orders::add);
        return orderMapper.orderToListResponse(orders);
    }

    public OrderResponseDto completeOrder(Long id) {
        OrderEntity orderEntity = orderRepo.findById(id).orElseThrow();
        orderEntity.setCompleted(true);
        return orderMapper.orderToDto(orderRepo.save(orderEntity));
    }

    private String formatLocalDateTime(LocalDateTime localDateTime, String acceptLanguage) {
        Locale locale = Locale.forLanguageTag(acceptLanguage);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss z").withLocale(locale);
        return localDateTime.format(formatter);
    }
}
