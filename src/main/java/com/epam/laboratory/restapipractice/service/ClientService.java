package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.model.Client;
import com.epam.laboratory.restapipractice.repository.impl.ClientRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepoImpl clientRepoImpl;

    public ClientEntity registration(ClientEntity client) {
        return clientRepoImpl.saveClient(client);
    }

    public Client getClient(Long id) {
        ClientEntity client = clientRepoImpl.findClientById(id);
        return Client.toModel(client);
    }

//    public List<Order> findClientOrders(Long id) {
//        ClientEntity client = clientRepo.findClientById(id).get();
//        return Client.toModel(client).getOrders();
//    }

    public Boolean updateClient(ClientEntity client) {
        clientRepoImpl.updateClient(client);
        return true;
    }

    public Boolean deleteClient(Long id) {
        clientRepoImpl.deleteClientById(id);
        return id != null;
    }

    public List<ClientEntity> getAllClients() {
        return clientRepoImpl.findAllClients();
    }
}
