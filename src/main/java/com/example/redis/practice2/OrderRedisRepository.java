package com.example.redis.practice2;

import org.springframework.data.repository.CrudRepository;

public interface OrderRedisRepository extends CrudRepository<ItemOrder, String> {
}