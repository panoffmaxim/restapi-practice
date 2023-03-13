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
    public List<CachedClientListResponse> getAllClientsFromCache() {//CachedClient List
        if (redisRepositoryImpl.findAllClientsFromCache() == null) {
            redisRepositoryImpl.add((ClientEntity) clientRepoImpl.findAllClients());
            return clientRepoImpl.findAllClients();
        } else {
            return redisRepositoryImpl.findAllClientsFromCache();
        }
    }
}
