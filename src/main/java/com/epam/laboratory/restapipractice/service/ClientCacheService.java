package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.impl.RedisRepositoryImpl;
import com.epam.laboratory.restapipractice.response.CachedClientListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@ClientBean
public class ClientCacheService {
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private RedisRepositoryImpl redisRepositoryImpl;

    public CachedClientListResponse getAllClientsFromCache() {
        if (redisRepositoryImpl.findAllClientsFromCache() == null) {
            List<ClientEntity> clientEntityList = StreamSupport.stream(clientRepo.findAll().spliterator(), false)
                    .collect(Collectors.toList());
            CachedClientListResponse cachedClientListResponse = CachedClientListResponse.fromEntityListToCachedList(clientEntityList);
            redisRepositoryImpl.add(cachedClientListResponse);
            return cachedClientListResponse;
        } else {
            return redisRepositoryImpl.findAllClientsFromCache();
        }
    }

    public CachedClientListResponse deleteAllClientsFromCache() {
        return redisRepositoryImpl.clearCacheByKey();
    }
}
