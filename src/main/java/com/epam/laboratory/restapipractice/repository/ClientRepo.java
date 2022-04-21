package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepo extends CrudRepository<ClientEntity, Long> {
    ClientEntity findByClientName(String clientName);
}
