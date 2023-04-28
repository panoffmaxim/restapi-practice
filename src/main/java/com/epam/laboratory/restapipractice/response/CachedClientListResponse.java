package com.epam.laboratory.restapipractice.response;

import com.epam.laboratory.restapipractice.entity.ClientEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CachedClientListResponse {
    private List<CachedClientResponse> cashedClientResponseList;

    public CachedClientListResponse() {
    }

    public CachedClientListResponse(List<CachedClientResponse> cashedClientResponseList) {
        this.cashedClientResponseList = cashedClientResponseList;
    }

    public List<CachedClientResponse> getCashedClientResponseList() {
        return cashedClientResponseList;
    }

    public void setCashedClientResponseList(List<CachedClientResponse> cashedClientResponseList) {
        this.cashedClientResponseList = cashedClientResponseList;
    }

    public static CachedClientListResponse fromEntityListToCachedList(List<ClientEntity> clientEntityList) {
        List<CachedClientResponse> cachedList = clientEntityList.stream().map(clientEntity -> new CachedClientResponse(clientEntity.getId(),
                        clientEntity.getClientName(), clientEntity.getOrders().stream()
                        .map(orderEntity -> new CachedClientResponse.CachedClientOrderResponse(orderEntity.getId(),
                                orderEntity.getCompleted(),
                                orderEntity.getDeliveryInf()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new CachedClientListResponse(cachedList);
    }
}
