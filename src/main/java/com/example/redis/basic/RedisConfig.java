package com.example.redis.basic;

import com.example.redis.practice4.domain.ItemDto;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
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

		// 시리얼라이저를 통일합니다.
		RedisSerializer<Object> serializer = springSessionDefaultRedisSerializer();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(serializer);
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(serializer);

		return template;
	}

	@Bean
	public RedisTemplate<String, String> cartTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(RedisSerializer.string());
		redisTemplate.setHashKeySerializer(RedisSerializer.string());
		redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Integer.class));
		return redisTemplate;
	}

	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
		return new GenericJackson2JsonRedisSerializer(sessionObjectMapper()); // 참고하신 스타일 유지!
	}

	// 1. 공통으로 사용할 ObjectMapper를 별도의 빈으로 등록하거나 메서드로 추출합니다.
	private ObjectMapper sessionObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		// Spring Security의 특수 객체들을 다룰 줄 아는 '전문가' 모듈 등록
		mapper.registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));

		// 타입 정보를 포함시켜야 나중에 역직렬화할 때 클래스를 정확히 찾아옵니다.
		mapper.activateDefaultTyping(
				LaissezFaireSubTypeValidator.instance,
				ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.PROPERTY
		);
		return mapper;
	}
}