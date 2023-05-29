package com.epam.laboratory.restapipractice.response;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;

import java.util.List;

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
