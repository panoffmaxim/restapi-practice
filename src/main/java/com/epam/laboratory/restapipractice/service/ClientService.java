package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.ClientEntityList;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.model.Client;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.impl.ClientRepoImpl;
import com.epam.laboratory.restapipractice.repository.impl.RedisRepositoryImpl;
import com.epam.laboratory.restapipractice.response.CachedClientResponse;
import com.epam.laboratory.restapipractice.response.ClientResponse;
import com.epam.laboratory.restapipractice.response.ClientsListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ClientBean
public class ClientService {
    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private ClientCacheService clientCacheService;
    @Autowired
    private RedisRepositoryImpl redisRepositoryImpl;

    public ClientEntity registration(ClientEntity client) {
        return clientRepo.saveClient(client);
    }

    public Client getClient(Long id) {
        ClientEntity client = clientRepo.findClientById(id);
        return Client.toModel(client);
    }

    public Boolean updateClient(ClientEntity client) {
        clientRepo.updateClient(client);
        return true;
    }

    public void deleteClient(Long id) {
        clientRepo.deleteClientById(id);
    }

    public static ClientEntityList fromCachedListToEntityList(List<CachedClientResponse> cachedList) {
        List<ClientEntity> cachedListToEntityList = cachedList.stream().map(cachedClientResponse -> new ClientEntity(cachedClientResponse.getId(),
                        cachedClientResponse.getClientName(), cachedClientResponse.getOrders().stream()
                        .map(cachedClientOrderResponse -> new OrderEntity(cachedClientOrderResponse.getId(),
                                cachedClientOrderResponse.getCompleted(),
                                cachedClientOrderResponse.getDeliveryInf()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ClientEntityList(cachedListToEntityList);
    }

    public ClientsListResponse getAllClients() {
        List<CachedClientResponse> cachedClientResponseList = clientCacheService.getAllClientsFromCache().getCashedClientResponseList();
        final List<ClientEntity> clientEntityList = fromCachedListToEntityList(cachedClientResponseList).getClientEntityList();
        final ClientsListResponse clientsListResponse = new ClientsListResponse(
                clientEntityList.stream().map(clientEntity -> new ClientResponse(clientEntity.getId(),
                                clientEntity.getClientName(),
                                clientEntity.getOrders().stream()
                                        .map(orderEntity -> new ClientResponse.ClientOrderResponse(orderEntity.getId(),
                                                orderEntity.getCompleted(),
                                                orderEntity.getDeliveryInf()))
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList())
        );
        return clientsListResponse;
    }
}
