package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.mapper.OrderMapper;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.epam.laboratory.restapipractice.constant.Constants.FORMAT;

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
        OrderEntity savedOrderEntity = orderRepo.save(orderEntity);
        OrderResponseDto createdOrder = orderMapper.orderToDto(savedOrderEntity);

        LocalDateTime creationDateTime = savedOrderEntity.getCreationDateTime();
        ZoneId clientZoneId = ZoneId.of(acceptTimezone);
        ZonedDateTime zonedUTC = creationDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedDateTime = zonedUTC.withZoneSameInstant(clientZoneId);
        DateTimeFormatter formatter = FORMAT.withLocale(Locale.forLanguageTag(acceptLanguage));
        String formattedDateTime = zonedDateTime.format(formatter);

        createdOrder.setCreationDateTime(formattedDateTime);
        return createdOrder;
    }

    public List<OrderResponseDto> getAllOrders(String acceptLanguage, String acceptTimezone) {
        return orderRepo.findAll()
                .stream()
                .map(orderEntity -> {
                    OrderResponseDto orderResponseDto = orderMapper.orderToDto(orderEntity);
                    LocalDateTime creationDateTime = orderEntity.getCreationDateTime();
                    ZoneId clientZoneId = ZoneId.of(acceptTimezone);
                    ZonedDateTime zonedUTC = creationDateTime.atZone(ZoneId.of("UTC"));
                    ZonedDateTime zonedDateTime = zonedUTC.withZoneSameInstant(clientZoneId);
                    DateTimeFormatter formatter = FORMAT.withLocale(Locale.forLanguageTag(acceptLanguage));
                    String formattedDateTime = zonedDateTime.format(formatter);
                    orderResponseDto.setCreationDateTime(formattedDateTime);
                    return orderResponseDto;
                })
                .collect(Collectors.toList());
    }

    public OrderResponseDto completeOrder(Long id) {
        OrderEntity orderEntity = orderRepo.findById(id).orElseThrow();
        orderEntity.setCompleted(true);
        return orderMapper.orderToDto(orderRepo.save(orderEntity));
    }
}
