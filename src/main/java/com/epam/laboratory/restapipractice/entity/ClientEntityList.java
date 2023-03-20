package com.epam.laboratory.restapipractice.entity;

import java.util.List;

public class ClientEntityList {
    private final List<ClientEntity> clientEntityList;

    public ClientEntityList(List<ClientEntity> clientEntityList) {
        this.clientEntityList = clientEntityList;
    }

    public List<ClientEntity> getClientEntityList() {
        return clientEntityList;
    }
}
