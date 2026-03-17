package com.example.redis.practice4.repository;

import com.example.redis.practice4.domain.ItemOrder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;

@Qualifier("orderRepository2")
public interface OrderRepository extends CrudRepository<ItemOrder, String> {

}