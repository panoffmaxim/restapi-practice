package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<OrderEntity, Long> {
}
