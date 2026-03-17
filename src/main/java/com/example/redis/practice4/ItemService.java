package com.example.redis.practice4;

import com.example.redis.practice4.domain.Item;
import com.example.redis.practice4.domain.ItemDto;
import com.example.redis.practice4.domain.ItemOrder;
import com.example.redis.practice4.repository.ItemRepository;
import com.example.redis.practice4.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ItemService {

	@Qualifier("itemRepository2")
	private final ItemRepository itemRepository;

	@Qualifier("orderRepository2")
	private final OrderRepository orderRepository;
	private final ZSetOperations<String, ItemDto> rankOps;

	public ItemService(
			ItemRepository itemRepository,
			OrderRepository orderRepository,
			RedisTemplate<String, ItemDto> rankTemplate
	) {
		this.itemRepository = itemRepository;
		this.orderRepository = orderRepository;
		this.rankOps = rankTemplate.opsForZSet();
	}

	public void purchase(Long id) {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		ItemOrder newOrder = ItemOrder.create(item, 1);
		orderRepository.save(newOrder);
		ItemDto dto = ItemDto.fromEntity(item);

		// 구매 진행 시마다 점수 증가
		// 기존 데이터가 없을 경우 새로 저장 후 점수 증가
		rankOps.incrementScore("soldRanks", dto, 1);
	}

	public List<ItemDto> getMostSold() {
		// rankOps.reverseRange: LinkedHashSet을 반환하므로, 정렬된 순서를 유지
		Set<ItemDto> ranks = rankOps.reverseRange("soldRanks", 0, 9);
		if (ranks == null) return Collections.emptyList();
		return ranks.stream().toList();
	}
}
