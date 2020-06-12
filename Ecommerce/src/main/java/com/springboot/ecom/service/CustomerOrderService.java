package com.springboot.ecom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.CustomerOrder;
import com.springboot.ecom.repository.CustomerOrderRepository;

@Service
public class CustomerOrderService {
	
	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	
	public CustomerOrder saveCustomerOrder(CustomerOrder customerOrder) {
		customerOrder = customerOrderRepository.save(customerOrder);
		return customerOrder;
	}
	
	public Optional<CustomerOrder> getCustomerOrderById(int orderId) {
		return customerOrderRepository.findById(orderId);
	}
	
	public void deleteCustomerOrder(int orderId) {
		customerOrderRepository.deleteById(orderId);
	}

}
