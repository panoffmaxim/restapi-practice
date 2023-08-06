package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.dto.OrderListResponseDto;
import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.mapper.OrderMapper;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import org.modelmapper.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.epam.laboratory.restapipractice.constant.Constants.FORMAT;
import static com.epam.laboratory.restapipractice.constant.Constants.TIME_ZONE;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final ClientRepo clientRepo;
    private final OrderMapper orderMapper;
    private final ZoneId serverZoneId;
    private final ModelMapper mapper;

    public OrderService(OrderRepo orderRepo, ClientRepo clientRepo, OrderMapper orderMapper, ModelMapper mapper) {
        this.orderRepo = orderRepo;
        this.clientRepo = clientRepo;
        this.orderMapper = orderMapper;
        this.serverZoneId = ZoneId.systemDefault();
        this.mapper = mapper;
    }

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, String acceptLanguage, String acceptTimezone) {
        ClientEntity client = clientRepo.findById(orderRequestDto.getClientId()).orElseThrow(() -> new EntityNotFoundException("Client not found"));

        OrderEntity orderEntity = orderMapper.orderToEntity(orderRequestDto);
        orderEntity.setClient(client);

        Provider<LocalDateTime> localDateTimeProvider = new AbstractProvider<>() {
            public LocalDateTime get() {
                ZonedDateTime currentDateTime;
                if (acceptTimezone != null && !acceptTimezone.isEmpty()) {
                    ZoneId clientZoneId = ZoneId.of(TIME_ZONE);
                    currentDateTime = ZonedDateTime.now(clientZoneId);
                } else {
                    currentDateTime = ZonedDateTime.now(serverZoneId);
                }
                return currentDateTime.toLocalDateTime();
            }
        };
        mapper.typeMap(OrderRequestDto.class, OrderEntity.class)
                .addMappings(m -> m.with(localDateTimeProvider)
                        .map(OrderRequestDto::getClientId, OrderEntity::setCreationDateTime));

        Locale currentLocale = Locale.forLanguageTag(acceptLanguage);
        TypeMap<OrderEntity, OrderResponseDto> propertyMapper = mapper.createTypeMap(OrderEntity.class, OrderResponseDto.class);
        Converter<LocalDateTime, String> localDateTimeToString = c -> c.getSource().format(FORMAT.withLocale(currentLocale));
        propertyMapper.addMappings(
                mapper -> mapper.using(localDateTimeToString).map(OrderEntity::getCreationDateTime, OrderResponseDto::setCreationDateTime)
        );

        OrderResponseDto orderResponseDto = orderMapper.orderToDto(orderRepo.save(orderEntity));
        return orderResponseDto;
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
