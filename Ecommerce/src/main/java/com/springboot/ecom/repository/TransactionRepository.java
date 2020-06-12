package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.ecom.model.CustomerOrder;
import com.springboot.ecom.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	
	List<Transaction> findByCustomerOrder(CustomerOrder customerOrder);

}
