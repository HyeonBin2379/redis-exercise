package com.example.redis.practice5.cart;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final String keyString = "cart:%s";
	private final RedisTemplate<String, String> cartTemplate;

	// 장바구니에 관한 redis의 데이터 타입으로는 hash를 사용
	private HashOperations<String, String, Integer> hashOps;

	@PostConstruct
	public void init() {
		this.hashOps = cartTemplate.opsForHash();
	}

	public void modifyCart(String sessionId, CartItemDto dto) {
		String key = keyString.formatted(sessionId);

		// 물품 조정(생성, 수정)
		// redis hash의 HINCRBY 연산 -> getCount()의 값이 음수면 수량 감소
		Long updatedCount = hashOps.increment(
				key,
				dto.getItem(),
				dto.getCount()
		);

		// 수량이 0 이하가 되면 장바구니에서 제거
		Optional.of(updatedCount)
				.filter(count -> count <= 0)
				.ifPresent(count -> hashOps.delete(key, dto.getItem()));
	}

	public CartDto getCart(String sessionId) {
		// 사용자의 장바구니가 비어 있는지 확인
		String key = keyString.formatted(sessionId);
		if (!Boolean.TRUE.equals(cartTemplate.hasKey(key))) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		// 생성된 장바구니는 3시간 뒤에 만료 -> 만료 시 redis에서 자동 삭제
		Date expireAt = Date.from(Instant.now().plus(3, ChronoUnit.HOURS));
		cartTemplate.expireAt(
				keyString.formatted(sessionId),
				expireAt
		);
		return CartDto.fromHashPairs(
				hashOps.entries(keyString.formatted(sessionId)),
				expireAt
		);
	}
}
