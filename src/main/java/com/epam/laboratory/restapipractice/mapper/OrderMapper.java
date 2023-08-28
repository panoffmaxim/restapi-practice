package com.epam.laboratory.restapipractice.mapper;

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

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.typeMap(OrderRequestDto.class, OrderEntity.class)
                .addMappings(m -> m.with(localDateTimeProvider)
                        .map(OrderRequestDto::getDeliveryInf, OrderEntity::setCreationDateTime))
                .addMapping(src -> Boolean.FALSE, OrderEntity::setCompleted);

        modelMapper.createTypeMap(OrderEntity.class, OrderResponseDto.class)
                .addMappings(mapper -> mapper.using(localDateTimeToString)
                        .map(OrderEntity::getCreationDateTime, OrderResponseDto::setCreationDateTime))
                .addMapping(id -> id.getClient().getId(), OrderResponseDto::setClientId);
    }

    public OrderEntity orderToEntity(OrderRequestDto orderRequestDto) {
        return Objects.isNull(orderRequestDto) ? null : modelMapper.map(orderRequestDto, OrderEntity.class);
    }

    public OrderResponseDto orderToDto(OrderEntity orderEntity) {
        return Objects.isNull(orderEntity) ? null : modelMapper.map(orderEntity, OrderResponseDto.class);
    }
}
