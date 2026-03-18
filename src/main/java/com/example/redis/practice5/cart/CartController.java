package com.example.redis.practice5.cart;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@PostMapping
	public CartDto modifyCart(
			@RequestBody CartItemDto itemDto,
			HttpSession session
	) {
		// 장바구니에 담을 물품과 그 수량은 클라이언트가 지정
		cartService.modifyCart(session.getId(), itemDto);
		return cartService.getCart(session.getId());
	}

	@GetMapping
	public CartDto getCart(
			HttpSession session
	) {
		log.info(session.getId());
		return cartService.getCart(session.getId());
	}
}
