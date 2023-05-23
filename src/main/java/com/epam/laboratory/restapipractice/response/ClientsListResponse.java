package com.epam.laboratory.restapipractice.response;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;

import java.util.List;
import java.util.stream.Collectors;

@ClientBean
public class ClientsListResponse {
    private final List<ClientResponse> clients;

    public ClientsListResponse(final List<ClientResponse> clients) {
        this.clients = clients;
    }

    public List<ClientResponse> getClients() {
        return clients;
    }
}
