package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@ClientBean
@Repository
public interface ClientRepo extends CrudRepository<ClientEntity, Long> {
    ClientEntity findByClientName(String clientName);
}
