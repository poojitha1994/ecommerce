package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.Product;
import com.springboot.ecom.repository.ProductRepository;

@Service
public class ProductService {
	
	
	@Autowired
	private ProductRepository repo;
	
	
	public List<Product> getAllProductsByUser(String addedBy){
		
		return repo.getProductsByUserId(addedBy);
		
	}
	
	public List<Product> getAllProducts(){
		
		return repo.findAll();
	}
	
	public Optional<Product> getProductbyId(int id){
		
		return repo.findById(id);
		
	}
	
	
	public void saveProduct(Product product){
		
		repo.save(product);
		
	}
	
	public void deleteProduct(int id) {
		repo.deleteById(id);
	}
	
	public void updateQuantity(int productId,int quantity) {
		repo.updateQuantity(productId,quantity);
	}

}
