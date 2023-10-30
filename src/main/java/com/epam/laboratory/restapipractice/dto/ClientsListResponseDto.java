package com.epam.laboratory.restapipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ClientsListResponseDto {
    @Schema(description = "Возвращает список клиентов")
    private final List<ClientResponseDto> clients;

    public ClientsListResponseDto(final List<ClientResponseDto> clients) {
        this.clients = clients;
    }

    public List<ClientResponseDto> getClients() {
        return clients;
    }
}
