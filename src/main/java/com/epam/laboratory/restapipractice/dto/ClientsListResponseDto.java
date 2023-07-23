package com.epam.laboratory.restapipractice.dto;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;

import java.util.List;

@ClientBean
public class ClientsListResponseDto {
    private final List<ClientResponseDto> clients;

    public ClientsListResponseDto(final List<ClientResponseDto> clients) {
        this.clients = clients;
    }

    public List<ClientResponseDto> getClients() {
        return clients;
    }
}
