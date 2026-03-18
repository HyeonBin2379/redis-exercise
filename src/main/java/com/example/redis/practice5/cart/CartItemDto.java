package com.example.redis.practice5.cart;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto implements Serializable {

	private String item;
	private Integer count;

	public static CartItemDto create(
			String item,
			Integer count
	) {
		return CartItemDto.builder()
				.item(item)
				.count(count)
				.build();
	}
}
