package com.example.redis.basic;

import com.example.redis.practice4.domain.ItemDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, com.example.redis.basic.domain.ItemDto> itemRedisTemplate(
			RedisConnectionFactory connectionFactory
	) {
		RedisTemplate<String, com.example.redis.basic.domain.ItemDto> template = new RedisTemplate<>();  // 1
		template.setConnectionFactory(connectionFactory);                 // 2
		template.setKeySerializer(RedisSerializer.string());              // 3-a
		template.setValueSerializer(RedisSerializer.json());              // 3-b
		return template;                                                  // 4
	}

	@Bean
	public RedisTemplate<String, ItemDto> rankTemplate(
			RedisConnectionFactory connectionFactory
	) {
		RedisTemplate<String, ItemDto> template = new RedisTemplate<>();  // 1
		template.setConnectionFactory(connectionFactory);                 // 2
		template.setKeySerializer(RedisSerializer.string());              // 3-a
		template.setValueSerializer(RedisSerializer.json());              // 3-b
		return template;                                                  // 4
	}


	@Bean
	public RedisTemplate<String, Integer> articleTemplate(
			RedisConnectionFactory redisConnectionFactory
	) {
		RedisTemplate<String, Integer> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(RedisSerializer.string());
		template.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
		return template;
	}

	@Bean
	public RedisTemplate<String, Object> sessionTemplate(
			RedisConnectionFactory redisConnectionFactory
	) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(RedisSerializer.string());
		template.setHashKeySerializer(new StringRedisSerializer());

		GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		template.setValueSerializer(jsonRedisSerializer);
		template.setHashValueSerializer(jsonRedisSerializer);
		return template;
	}

	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}
}