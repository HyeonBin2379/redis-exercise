package com.example.redis.practice5.cart;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto implements Serializable {

	@Builder.Default
	private Set<CartItemDto> items = new HashSet<>();
	private Date expireAt;

	public static CartDto fromHashPairs(
			Map<String, Integer> entries,
			Date expireAt
	) {
		Set<CartItemDto> items = entries.entrySet().stream()
				.map(entry -> CartItemDto.create(entry.getKey(), entry.getValue()))
				.collect(Collectors.toUnmodifiableSet());

		return CartDto.builder()
				.items(items)
				.expireAt(expireAt)
				.build();
	}
}
