package com.epam.laboratory.restapipractice.mapper;

import com.epam.laboratory.restapipractice.dto.OrderListResponseDto;
import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static com.epam.laboratory.restapipractice.constant.Constants.FORMAT;

@Component
public class OrderMapper {
    private final ModelMapper modelMapper;
    private final Converter<LocalDateTime, String> localDateTimeToString = c -> c.getSource().format(FORMAT);
    private final Provider<LocalDateTime> localDateTimeProvider = new AbstractProvider<>() {
        public LocalDateTime get() {
            ZonedDateTime currentDateTime = ZonedDateTime.now();
            return currentDateTime.toLocalDateTime();
        }
    };

    public OrderMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(OrderEntity.class, OrderResponseDto.class)
                .addMappings(mapper -> mapper.using(localDateTimeToString)
                        .map(OrderEntity::getCreationDateTime, OrderResponseDto::setCreationDateTime));

        modelMapper.typeMap(OrderRequestDto.class, OrderEntity.class)
                .addMappings(m -> m.with(localDateTimeProvider)
                        .map(OrderRequestDto::getClientId, OrderEntity::setCreationDateTime));
    }

    public OrderEntity orderToEntity(OrderRequestDto orderRequestDto) {
        return Objects.isNull(orderRequestDto) ? null : modelMapper.map(orderRequestDto, OrderEntity.class);
    }

    public OrderResponseDto orderToDto(OrderEntity orderEntity) {
        return Objects.isNull(orderEntity) ? null : modelMapper.map(orderEntity, OrderResponseDto.class);
    }

    public OrderListResponseDto orderToListResponse(List<OrderEntity> orderEntity) {
        return Objects.isNull(orderEntity) ? null : modelMapper.map(orderEntity, OrderListResponseDto.class);
    }
}
