package com.springboot.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.Cart;
import com.springboot.ecom.model.CartProduct;
import com.springboot.ecom.repository.CartProductRepository;

@Service
public class CartProductService {
	
	@Autowired
	private CartProductRepository cartProductRepository;
	
	public void saveCartProduct(CartProduct cartProduct) {
		cartProductRepository.save(cartProduct);
	}
	
	public CartProduct getCartProductByCartIdAndProductId(int cartId, int productId) {
		return cartProductRepository.getCartProductByCartIdAndProductId(cartId,productId);
	}

	public List<CartProduct> getCartProduct(int cartId) {
		
		return cartProductRepository.findByCartId(cartId);
	}
	
	public void deleteCartProduct(int cartProductId) {
		cartProductRepository.deleteById(cartProductId);
	}
}
