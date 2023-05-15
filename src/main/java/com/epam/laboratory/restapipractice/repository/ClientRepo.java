package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@ClientBean
public interface ClientRepo extends CrudRepository<ClientEntity, Long> {
    //    ClientEntity saveClient(ClientEntity client);
//    Boolean updateClient(ClientEntity client);
//    Boolean deleteClientById(Long id);
//    ClientEntity findClientById(Long id);
//    List<ClientEntity> findAllClients();
    ClientEntity findByClientName(String clientName);
}
