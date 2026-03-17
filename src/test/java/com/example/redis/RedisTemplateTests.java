package com.example.redis;

import com.example.redis.basic.domain.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void stringOpTest() {
		// RedisTemplate에 설정된 타입을 바탕으로 Redis 문자열을 조작
		ValueOperations<String, String> ops
				= stringRedisTemplate.opsForValue();

		// SET simpleKey simpleValue
		ops.set("simpleKey", "simpleValue");
		System.out.println(ops.get("simpleKey"));
	}

	@Test
	public void stringSetOpsTest() {
		SetOperations<String, String> setOps
				= stringRedisTemplate.opsForSet();
		setOps.add("hobbies", "games");
		setOps.add("hobbies", "coding", "alcohol", "games");
		System.out.println(setOps.size("hobbies"));
	}

	@Test
	public void stringHashOpsTest() {
		HashOperations<String, String, String> hashOps
				= stringRedisTemplate.opsForHash();
		hashOps.put("user", "name", "tester");
		hashOps.put("user", "email", "tester@tester.com");

		System.out.println(hashOps.entries("user"));
		System.out.println(hashOps.keys("user"));
		System.out.println(hashOps.values("user"));
	}

	@Test
	public void redisOpsTest() {
		stringRedisTemplate.expire("hobbies", 10, TimeUnit.SECONDS);
		stringRedisTemplate.delete("simpleKey");
	}


	@Autowired
	private RedisTemplate<String, ItemDto> itemRedisTemplate;

	@Test
	public void itemRedisTemplateTest() {
		ValueOperations<String, ItemDto> ops = itemRedisTemplate.opsForValue();
		ops.set("my:keyboard", ItemDto.builder()
				.name("Mechanical Keyboard")
				.price(300000)
				.description("Expensive 😢")
				.build());
		System.out.println(ops.get("my:keyboard"));

		ops.set("my:mouse", ItemDto.builder()
				.name("mouse mice")
				.price(100000)
				.description("Expensive 😢")
				.build());
		System.out.println(ops.get("my:mouse"));
	}
}
