package com.springboot.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.OrderLine;
import com.springboot.ecom.repository.OrderLineRepository;

@Service
public class OrderLineService {

	@Autowired
	private OrderLineRepository orderLineRepository;
	
	public void saveOrderLine(OrderLine orderLine) {
		
		orderLineRepository.save(orderLine);
	}
	
public void deleteOrderLine(int orderLineId) {
		
		orderLineRepository.deleteById(orderLineId);
	}

public List<OrderLine> getOrderLineByOrderId(int orderId){
	
	
	return orderLineRepository.findByOrderId(orderId);
}
}
