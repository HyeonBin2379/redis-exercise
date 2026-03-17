package com.example.redis.practice2;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("order")
public class ItemOrder {

	@Id
	private String id;
	private String item;
	private Integer count;
	private Long totalPrice;
	private String status;

	public static ItemOrder create(
			String item,
			Integer count,
			Long totalPrice,
			String status
	) {
		return ItemOrder.builder()
				.item(item)
				.count(count)
				.totalPrice(totalPrice)
				.status(status)
				.build();
	}

	public void update(
			String item,
			Integer count,
			Long totalPrice,
			String status) {
		if (item != null) {
			this.item = item;
		}
		if (count != null && count > 0) {
			this.count = count;
		}
		if (totalPrice != null && totalPrice > 0) {
			this.totalPrice = totalPrice;
		}
		if (status != null) {
			this.status = status;
		}
	}
}
