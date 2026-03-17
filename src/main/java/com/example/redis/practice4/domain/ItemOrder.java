package com.example.redis.practice4.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	private Integer count;

	public static ItemOrder create(
			Item item,
			int count
	) {
		return ItemOrder.builder()
				.item(item)
				.count(count)
				.build();
	}

	public void update(
			Item item,
			Integer count
	) {
		if (item != null) {
			this.item = item;
		}
		if (count != null && count > 0) {
			this.count = count;
		}
	}
}
