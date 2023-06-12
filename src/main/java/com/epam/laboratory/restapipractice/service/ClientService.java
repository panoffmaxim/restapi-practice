package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.dto.ClientDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.ClientEntityList;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.mapper.ClientMapper;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
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
    private ClientCacheService clientCacheService;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ClientMapper clientMapper;

    public ClientDto registration(ClientDto clientDto) {
        return clientMapper.toDto(clientRepo.save(clientMapper.toEntity(clientDto)));
    }

    public ClientDto getClient(Long id) {
            return clientMapper.toDto(clientRepo.findById(id).orElseThrow());
    }

    public ClientDto updateClient(ClientDto clientDto) {
        ClientEntity clientEntity = clientMapper.toEntity(clientDto);
        ClientEntity existingClient = clientRepo.findById(clientEntity.getId()).orElseThrow();
        existingClient.setClientName(clientEntity.getClientName());
        existingClient.setPhone(clientEntity.getPhone());
        existingClient.setOrders(clientEntity.getOrders());
        ClientEntity updatedClient = clientRepo.save(existingClient);
        return clientMapper.toDto(updatedClient);
    }

    public void deleteClient(Long id) {
        clientRepo.deleteById(id);
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
        return new ClientsListResponse(
                clientEntityList.stream().map(clientEntity -> new ClientResponse(clientEntity.getId(),
                                clientEntity.getClientName(),
                                clientEntity.getOrders().stream()
                                        .map(orderEntity -> new ClientResponse.ClientOrderResponse(orderEntity.getId(),
                                                orderEntity.getCompleted(),
                                                orderEntity.getDeliveryInf()))
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList())
        );
    }
}
