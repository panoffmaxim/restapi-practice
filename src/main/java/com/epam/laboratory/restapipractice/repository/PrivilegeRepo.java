package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.entity.PrivilegeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo {
    PrivilegeEntity findByName(String name);
    void delete(PrivilegeEntity privilegeEntity);
}
