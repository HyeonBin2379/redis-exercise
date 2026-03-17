package com.example.redis.practice2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ItemOrderDto> readAll() {
		return orderService.readAll();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ItemOrderDto readOne(@PathVariable("id") String id) {
		return orderService.read(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ItemOrderDto create(@RequestBody ItemOrderDto orderDto) {
		return orderService.create(orderDto);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ItemOrderDto update(@PathVariable("id") String id, @RequestBody ItemOrderDto orderDto) {
		return orderService.update(id, orderDto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		orderService.delete(id);
	}
}
