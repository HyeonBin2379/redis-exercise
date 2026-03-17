package com.example.redis.practice2;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrderDto {

	private String item;
	private Integer count;
	private Long totalPrice;
	private String status;

	public static ItemOrderDto from(ItemOrder itemOrder) {
		return ItemOrderDto.builder()
				.item(itemOrder.getItem())
				.count(itemOrder.getCount())
				.totalPrice(itemOrder.getTotalPrice())
				.status(itemOrder.getStatus())
				.build();
	}
}
