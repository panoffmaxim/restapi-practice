package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<ClientEntity, Long> {
    ClientEntity findByClientName(String clientName);
}
