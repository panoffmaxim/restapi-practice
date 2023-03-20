package com.epam.laboratory.restapipractice.response;

import com.epam.laboratory.restapipractice.entity.ClientEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CachedClientListResponse {
    private final List<CachedClientResponse> cashedClientResponseList;

    public CachedClientListResponse(final List<CachedClientResponse> cashedClientResponseList) {
        this.cashedClientResponseList = cashedClientResponseList;
    }

    public List<CachedClientResponse> getCashedClients() {
        return cashedClientResponseList;
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
