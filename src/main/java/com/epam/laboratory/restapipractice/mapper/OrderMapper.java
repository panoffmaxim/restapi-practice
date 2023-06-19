package com.epam.laboratory.restapipractice.mapper;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderMapper {
    @Autowired
    private ModelMapper modelMapper;

    public OrderEntity OrderToEntity(OrderRequestDto orderRequestDto) {
        return Objects.isNull(orderRequestDto) ? null : modelMapper.map(orderRequestDto, OrderEntity.class);
    }

    public OrderResponseDto OrderToDto(OrderEntity orderEntity) {
        return Objects.isNull(orderEntity) ? null : modelMapper.map(orderEntity, OrderResponseDto.class);
    }
}
