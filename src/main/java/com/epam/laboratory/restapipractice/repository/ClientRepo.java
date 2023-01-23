package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.entity.ClientEntity;

import java.util.List;

@ClientBean
public interface ClientRepo {
    ClientEntity saveClient(ClientEntity client);
    Boolean updateClient(ClientEntity client);
    Boolean deleteClientById(Long id);
    ClientEntity findClientById(Long id);
    List<ClientEntity> findAllClients();
    ClientEntity findByClientName(String clientName);
}
