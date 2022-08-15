package com.epam.laboratory.restapipractice.response;

import java.util.List;

public class ClientsListResponse {
    private final List<ClientResponse> clients;

    public ClientsListResponse(final List<ClientResponse> clients) {
        this.clients = clients;
    }

    public List<ClientResponse> getClients() {
        return clients;
    }
}
