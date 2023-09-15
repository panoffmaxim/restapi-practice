package com.epam.laboratory.restapipractice.mapper;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import org.modelmapper.AbstractProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

import static com.epam.laboratory.restapipractice.constant.Constants.ZONE_UTC_0;

@Component
public class OrderMapper {
    private final ModelMapper modelMapper;
    private final Provider<LocalDateTime> localDateTimeProvider = new AbstractProvider<>() {
        public LocalDateTime get() {
            ZonedDateTime currentDateTime = ZonedDateTime.now(ZONE_UTC_0);
            return currentDateTime.toLocalDateTime();
        }
    };

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.typeMap(OrderRequestDto.class, OrderEntity.class)
                .addMappings(m -> m.with(localDateTimeProvider)
                        .map(OrderRequestDto::getDeliveryInf, OrderEntity::setCreationDateTime))
                .addMapping(src -> Boolean.FALSE, OrderEntity::setCompleted);
    }

    public OrderEntity orderToEntity(OrderRequestDto orderRequestDto) {
        return Objects.isNull(orderRequestDto) ? null : modelMapper.map(orderRequestDto, OrderEntity.class);
    }

    public OrderResponseDto orderToDto(OrderEntity orderEntity) {
        return Objects.isNull(orderEntity) ? null : modelMapper.map(orderEntity, OrderResponseDto.class);
    }
}
