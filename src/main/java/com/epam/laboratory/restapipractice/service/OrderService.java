package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.mapper.OrderMapper;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import com.epam.laboratory.restapipractice.response.OrderListResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.time.format.DateTimeFormatter;
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
            ZoneId clientZoneId = ZoneId.of(acceptTimezone);
            currentDateTime = ZonedDateTime.now(clientZoneId);
        } else {
            currentDateTime = ZonedDateTime.now(serverZoneId);
        }
        String formattedDateTime = formatZonedDateTime(currentDateTime, acceptLanguage);

        OrderEntity orderEntity = orderMapper.orderToEntity(orderRequestDto);

        orderEntity.setCreationDateTime(formattedDateTime);
        orderEntity.setClient(client);
        OrderResponseDto orderResponseDto = orderMapper.orderToDto(orderRepo.save(orderEntity));
        orderResponseDto.setCreationDateTime(orderEntity.getCreationDateTime());
        return orderResponseDto;
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

    private String formatZonedDateTime(ZonedDateTime zonedDateTime, String acceptLanguage) {
        Locale locale = Locale.forLanguageTag(acceptLanguage);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss 'GMT'Z", locale);
        return zonedDateTime.format(formatter);
    }
}
