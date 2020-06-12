package com.springboot.ecom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.UserRepository;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository userrepository;
	
	public Optional<User> getUserById(String emailId) {
		return userrepository.findById(emailId);
		
	}
	
	public boolean checkUserIfExist(String emailId) {
		return userrepository.existsById(emailId);
	}
	
	public void saveUser(User user) {
		userrepository.save(user);
	}

}
