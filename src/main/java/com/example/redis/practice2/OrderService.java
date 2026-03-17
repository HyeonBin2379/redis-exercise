package com.example.redis.practice2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRedisRepository orderRedisRepository;

	public List<ItemOrderDto> readAll() {
		List<ItemOrderDto> orders = new ArrayList<>();
		orderRedisRepository.findAll()
				.forEach(itemOrder -> orders.add(ItemOrderDto.from(itemOrder)));
		return orders;
	}

	public ItemOrderDto read(String id) {
		return orderRedisRepository.findById(id)
				.map(ItemOrderDto::from)
				.orElseThrow();
	}

	public ItemOrderDto create(ItemOrderDto orderDto) {
		ItemOrder order = ItemOrder.create(
				orderDto.getItem(),
				orderDto.getCount(),
				orderDto.getTotalPrice(),
				orderDto.getStatus()
		);
		ItemOrder saved = orderRedisRepository.save(order);
		return ItemOrderDto.from(saved);
	}

	public ItemOrderDto update(String id, ItemOrderDto orderDto) {
		ItemOrder order = orderRedisRepository.findById(id).orElseThrow();
		order.update(
				orderDto.getItem(),
				orderDto.getCount(),
				order.getTotalPrice(),
				orderDto.getStatus()
		);
		ItemOrder saved = orderRedisRepository.save(order);
		return ItemOrderDto.from(saved);
	}

	public void delete(String id) {
		orderRedisRepository.deleteById(id);
	}
}
