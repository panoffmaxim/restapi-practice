package com.epam.laboratory.restapipractice.response;

import java.util.List;

public class CachedClientListResponse {
    private final List<CachedClientResponse> cashedClientResponseList;

    public CachedClientListResponse(final List<CachedClientResponse> cashedClientResponseList) {
        this.cashedClientResponseList = cashedClientResponseList;
    }

    public List<CachedClientResponse> getCashedClients() {
        return cashedClientResponseList;
    }
}
