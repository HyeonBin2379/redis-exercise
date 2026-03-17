package com.example.redis.basic.repository;

import com.example.redis.basic.domain.Item;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;

@Qualifier("itemRepository1")
public interface ItemRedisRepository extends CrudRepository<Item, Long> {
}
