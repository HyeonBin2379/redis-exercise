package com.example.redis.practice3;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

	private final RedisTemplate<String, Integer> articleTemplate;

	private ValueOperations<String, Integer> ops;

	@PostConstruct
	public void init() {
		this.ops = articleTemplate.opsForValue();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void read(
			@PathVariable("id") Long id
	) {
		ops.increment("articles:%d".formatted(id));
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Integer> readAll() {
		return ops.multiGet(List.of("articles:1", "articles:2", "articles:3"));
	}
}
