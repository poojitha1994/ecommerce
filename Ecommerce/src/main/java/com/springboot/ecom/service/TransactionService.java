package com.springboot.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.CustomerOrder;
import com.springboot.ecom.model.Transaction;
import com.springboot.ecom.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
	
	public List<Transaction> getTransactionByOrder(CustomerOrder customerOrder){
		return transactionRepository.findByCustomerOrder(customerOrder);
	}
	
}
