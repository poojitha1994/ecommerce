package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.ecom.model.OrderLine;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {

	@Query("SELECT OL FROM OrderLine OL WHERE OL.customerOrder.orderId = :orderId")
	List<OrderLine> findByOrderId(int orderId);

}
