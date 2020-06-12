package com.springboot.ecom.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.ecom.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query("SELECT p FROM Product p WHERE p.user.emailId= :emailId" )
	List<Product> getProductsByUserId(String emailId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.quantity= :quantity WHERE p.productId= :productId" )
	void updateQuantity(int productId, int quantity);
}
