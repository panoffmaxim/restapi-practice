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

//    public static ClientsListResponse fromCachedListToEntityList(List<CachedClientResponse> cachedList) {
//        List<ClientResponse> cachedListToEntityList = cachedList.stream().map(cachedClientResponse -> new ClientResponse(cachedClientResponse.getId(),
//                        cachedClientResponse.getClientName(), cachedClientResponse.getOrders().stream()
//                        .map(cachedClientOrderResponse -> new ClientResponse.ClientOrderResponse(cachedClientOrderResponse.getId(),
//                                cachedClientOrderResponse.getCompleted(),
//                                cachedClientOrderResponse.getDeliveryInf()))
//                        .collect(Collectors.toList())))
//                .collect(Collectors.toList());
//        return new ClientsListResponse(cachedListToEntityList);
//    }

}
