package com.epam.laboratory.restapipractice.mapper;

import com.epam.laboratory.restapipractice.dto.ClientRequestDto;
import com.epam.laboratory.restapipractice.dto.ClientResponseDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClientMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ClientEntity ClientToEntity(ClientRequestDto clientRequestDto) {
        return Objects.isNull(clientRequestDto) ? null : modelMapper.map(clientRequestDto, ClientEntity.class);
    }

    public ClientResponseDto ClientToDto(ClientEntity clientEntity) {
        return Objects.isNull(clientEntity) ? null : modelMapper.map(clientEntity, ClientResponseDto.class);
    }
}
