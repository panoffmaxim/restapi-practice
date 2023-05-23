package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.repository.impl.ClientRepoImpl;
import com.epam.laboratory.restapipractice.repository.impl.RedisRepositoryImpl;
import com.epam.laboratory.restapipractice.response.CachedClientListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ClientBean
public class ClientCacheService {
    @Autowired
    private ClientRepoImpl clientRepoImpl;
    @Autowired
    private RedisRepositoryImpl redisRepositoryImpl;

    public CachedClientListResponse getAllClientsFromCache() {
        if (redisRepositoryImpl.findAllClientsFromCache() == null) {
            List<ClientEntity> clientEntityList = clientRepoImpl.findAllClients();
            CachedClientListResponse cachedClientListResponse = CachedClientListResponse.fromEntityListToCachedList(clientEntityList);
            redisRepositoryImpl.add(cachedClientListResponse);
            return cachedClientListResponse;
        } else {
            return redisRepositoryImpl.findAllClientsFromCache();
        }
    }

    public void deleteAllClientsFromCache() {
        redisRepositoryImpl.clearCacheByKey();
    }
}
