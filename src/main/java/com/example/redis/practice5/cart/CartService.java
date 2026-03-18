package com.example.redis.practice5.cart;

public interface CartService {


	void modifyCart(String id, CartItemDto itemDto);

	CartDto getCart(String id);
}
