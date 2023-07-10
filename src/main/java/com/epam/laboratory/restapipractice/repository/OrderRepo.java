package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository<OrderEntity, Long> {
}
