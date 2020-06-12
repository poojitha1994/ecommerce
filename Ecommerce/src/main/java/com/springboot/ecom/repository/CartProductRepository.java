package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.ecom.model.Cart;
import com.springboot.ecom.model.CartProduct;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer>{
	
	@Query("SELECT CP FROM CartProduct CP WHERE CP.cart.cartId = :cartId")
	List<CartProduct> findByCartId(int cartId);
	
	@Query("SELECT CP FROM CartProduct CP WHERE CP.cart.cartId = :cartId AND CP.product.productId = :productId")
	CartProduct getCartProductByCartIdAndProductId(int cartId, int productId);

}
