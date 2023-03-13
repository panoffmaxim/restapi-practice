package com.epam.laboratory.restapipractice.response;

public class CachedClientResponse {
    private final Long id;
    private final String clientName;

    public CachedClientResponse(Long id, String clientName) {
        this.id = id;
        this.clientName = clientName;
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }
}
