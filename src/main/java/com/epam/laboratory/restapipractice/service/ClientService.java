package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.model.Client;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;


    public ClientEntity create(ClientEntity client) {
        return clientRepo.save(client);
    }

    public Client read(Long id) {
        ClientEntity client = clientRepo.findById(id).get();
        return Client.toModel(client);
    }

    public List<OrderEntity> findClientOrders(ClientEntity client) {
        List<OrderEntity> orders = client.getOrders();
        return orders;
    }

    public Boolean update(ClientEntity client, Long id) {
        if (clientRepo.findById(id).equals(id)) {
            client.setId(id);
            clientRepo.save(client);
            return true;
        }
        return false;
    }

    public Boolean delete(Long id) {
        clientRepo.deleteById(id);
        return id != null;
    }
}
