package com.epam.laboratory.restapipractice.dto;

import java.util.List;

public class ClientsListResponseDto {
    private final List<ClientResponseDto> clients;

    public ClientsListResponseDto(final List<ClientResponseDto> clients) {
        this.clients = clients;
    }

    public List<ClientResponseDto> getClients() {
        return clients;
    }
}
