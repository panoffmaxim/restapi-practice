package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.entity.RoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo {
    RoleEntity findByName(String name);
}
