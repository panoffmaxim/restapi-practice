package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.dto.ClientRequestDto;
import com.epam.laboratory.restapipractice.dto.ClientResponseDto;
import com.epam.laboratory.restapipractice.dto.ClientsListResponseDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.ClientEntityList;
import com.epam.laboratory.restapipractice.mapper.ClientMapper;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.response.CachedClientResponse;
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

    public ClientResponseDto registration(ClientRequestDto clientRequestDto) {
        return clientMapper.clientToDto(clientRepo.save(clientMapper.clientToEntity(clientRequestDto)));
    }

    public ClientResponseDto getClient(Long id) {
        return clientMapper.clientToDto(clientRepo.findById(id).orElseThrow());
    }

    public ClientResponseDto updateClient(ClientRequestDto clientRequestDto) {
        ClientEntity clientEntity = clientMapper.clientToEntity(clientRequestDto);
        ClientEntity existingClient = clientRepo.findById(clientEntity.getId()).orElseThrow();
        existingClient.setClientName(clientEntity.getClientName());
        existingClient.setPhone(clientEntity.getPhone());
        ClientEntity updatedClient = clientRepo.save(existingClient);
        return clientMapper.clientToDto(updatedClient);
    }

    public void deleteClient(Long id) {
        clientRepo.deleteById(id);
    }

    public static ClientEntityList fromCachedListToEntityList(List<CachedClientResponse> cachedList) {
        List<ClientEntity> cachedListToEntityList = cachedList.stream().map(cachedClientResponse -> new ClientEntity(cachedClientResponse.getId(),
                        cachedClientResponse.getClientName(), cachedClientResponse.getPhone()))
                .collect(Collectors.toList());
        return new ClientEntityList(cachedListToEntityList);
    }

    public ClientsListResponseDto getAllClients() {
        List<CachedClientResponse> cachedClientResponseList = clientCacheService.getAllClientsFromCache().getCashedClientResponseList();
        final List<ClientEntity> clientEntityList = fromCachedListToEntityList(cachedClientResponseList).getClientEntityList();
        return new ClientsListResponseDto(
                clientEntityList.stream().map(clientEntity -> new ClientResponseDto(clientEntity.getId(),
                                clientEntity.getClientName(),
                                clientEntity.getPhone()))
                        .collect(Collectors.toList())
        );
    }
}
