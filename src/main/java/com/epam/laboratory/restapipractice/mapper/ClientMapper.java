package com.epam.laboratory.restapipractice.mapper;

import com.epam.laboratory.restapipractice.dto.ClientDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClientMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ClientEntity toEntity(ClientDto clientDto) {
        return Objects.isNull(clientDto) ? null : modelMapper.map(clientDto, ClientEntity.class);
    }

    public ClientDto toDto(ClientEntity clientEntity) {
        return Objects.isNull(clientEntity) ? null : modelMapper.map(clientEntity, ClientDto.class);
    }
}
