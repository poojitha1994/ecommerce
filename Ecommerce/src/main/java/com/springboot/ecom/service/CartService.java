package com.springboot.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.Cart;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	public Cart getCartByUser(User user) {
		
		return cartRepository.findByUser(user.getEmailId());
		
	}
	
	public Cart createCart(Cart cart) {
		
		cart = cartRepository.save(cart);
		return cart;
		
	}

}
