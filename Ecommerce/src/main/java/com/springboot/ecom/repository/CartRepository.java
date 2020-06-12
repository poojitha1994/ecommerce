package com.springboot.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.ecom.model.Cart;
import com.springboot.ecom.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	
	Cart findByUser(User user);
	
	@Query("SELECT c FROM Cart c WHERE c.user.emailId= :emailId" )
	Cart findByUser(String emailId);
	
	/*
	 * @Query("INSERT INTO Cart(user) VALUES(:user)" ) void saveCart(User user);
	 */

}
