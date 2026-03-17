package com.example.redis;

import com.example.redis.basic.domain.Item;
import com.example.redis.basic.repository.ItemRedisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisRepositoryTests {

	@Autowired
	private ItemRedisRepository itemRedisRepository;

	@Test
	public void createTest() {
		Item item = Item.builder()
				.id(1L)
				.name("keyboard")
				.description("Very Expensive Keyboard")
				.price(100000)
				.build();
		itemRedisRepository.save(item);
	}

	@Test
	public void readOneTest() {
		Item item = itemRedisRepository.findById(1L)
				.orElseThrow();
		System.out.println(item.getDescription());
	}

	@Test
	public void updateTest() {
		Item item = itemRedisRepository.findById(1L)
				.orElseThrow();
		item.setDescription("On Sale!!!");
		item = itemRedisRepository.save(item);
		System.out.println(item.getDescription());
	}

	@Test
	public void deleteTest() {
		itemRedisRepository.deleteById(1L);
	}
}
