package com.example.redis.practice4.repository;

import com.example.redis.practice4.domain.Item;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;

@Qualifier("itemRepository2")
public interface ItemRepository extends CrudRepository<Item, Long> {
}
