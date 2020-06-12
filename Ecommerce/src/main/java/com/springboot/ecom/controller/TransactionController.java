package com.springboot.ecom.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.ecom.model.CustomerOrder;
import com.springboot.ecom.model.Transaction;
import com.springboot.ecom.service.CustomerOrderService;
import com.springboot.ecom.service.OrderLineService;
import com.springboot.ecom.service.TransactionService;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerOrderService customerOrderService;

    
    /**
     * 
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/transaction/{orderId}", method = RequestMethod.GET)
    public ModelAndView createTransaction(@PathVariable(name = "orderId") int orderId) {

        Optional<CustomerOrder> customerOrder = customerOrderService.getCustomerOrderById(orderId);

        Transaction transaction = new Transaction();
        transaction.setCustomerOrder(customerOrder.get());
        transactionService.saveTransaction(transaction);

        ModelAndView mvc = new ModelAndView("paymentdone");
        int customerOrderId = customerOrder.get().getOrderId();
        List<Transaction> transactionList = transactionService.getTransactionByOrder(customerOrder.get());
        mvc.addObject("customerOrderId", customerOrderId);
        mvc.addObject("transactionList", transactionList);
        return mvc;
    }

}
