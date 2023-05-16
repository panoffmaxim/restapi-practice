package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.ClientEntityList;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.model.Client;
import com.epam.laboratory.restapipractice.model.Order;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.redis.impl.RedisRepositoryImpl;
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
//    @Autowired
//    private ClientRepoImpl clientRepoImpl;

    @Autowired
    private ClientCacheService clientCacheService;
    @Autowired
    private RedisRepositoryImpl redisRepositoryImpl;

//    public ClientEntity registration(ClientEntity client) {
//        return clientRepoImpl.saveClient(client);
//    }
//
//    public Client getClient(Long id) {
//        ClientEntity client = clientRepoImpl.findClientById(id);
//        return Client.toModel(client);
//    }
//
////    public List<Order> findClientOrders(Long id) {
////        ClientEntity client = clientRepo.findClientById(id).get();
////        return Client.toModel(client).getOrders();
////    }
//
//    public Boolean updateClient(ClientEntity client) {
//        clientRepoImpl.updateClient(client);
//        return true;
//    }
//
//    public Boolean deleteClient(Long id) {
//        clientRepoImpl.deleteClientById(id);
//        return id != null;
//    }

    @Autowired
    private ClientRepo clientRepo;


    public ClientEntity registration(ClientEntity client) {
        return clientRepo.save(client);
    }

    public Client getClient(Long id) {
        ClientEntity client = clientRepo.findById(id).get();
        return Client.toModel(client);
    }

    public List<Order> findClientOrders(Long id) {
        ClientEntity client = clientRepo.findById(id).get();
        return Client.toModel(client).getOrders();
    }

    public Boolean updateClient(ClientEntity client) {
//        if (clientRepo.findById(id).equals(id)) {
//            client.setId(id);
//            clientRepo.save(client);
//            return true;
//        }
        return clientRepo.save(client) != null;
    }

    public Boolean deleteClient(Long id) {
        clientRepo.deleteById(id);
        return id != null;
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
