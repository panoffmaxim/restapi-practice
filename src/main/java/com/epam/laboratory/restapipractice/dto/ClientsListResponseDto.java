package com.epam.laboratory.restapipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;

@Value
public class ClientsListResponseDto {
    @Schema(description = "Возвращает список клиентов")
    private final List<ClientResponseDto> clients;
}
