package com.springboot.ecom.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.ecom.model.CartProduct;
import com.springboot.ecom.model.CustomerOrder;
import com.springboot.ecom.model.OrderLine;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.CartProductService;
import com.springboot.ecom.service.CustomerOrderService;
import com.springboot.ecom.service.OrderLineService;
import com.springboot.ecom.service.ProductService;

@Controller
public class OrderLineController {

    @Autowired
    private OrderLineService orderLineService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private CartProductService cartProductService;

    @Autowired
    private ProductService productService;
    
    /**
     * 
     * @param productId
     * @param httpSession
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/order/{productId}", method = RequestMethod.POST)
    public String placeOrderSingleProduct(@PathVariable(name = "productId") int productId, HttpSession httpSession) {

        Optional<User> user = (Optional<User>) httpSession.getAttribute(UserController.SESSION_ATTRIB_LOGIN_USER);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setUser(user.get());
        customerOrderService.saveCustomerOrder(customerOrder);
        System.out.println("Order header created successfully");

        return "redirect:/products";

    }
    
    /**
     * 
     * @param cartId
     * @param httpSession
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/cartorder/{cartId}", method = RequestMethod.GET)
    public String placeOrderFromCart(@PathVariable(name = "cartId") int cartId, HttpSession httpSession) {
    	User user = (User) httpSession.getAttribute(UserController.SESSION_ATTRIB_LOGIN_USER);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setUser(user);
        customerOrder = customerOrderService.saveCustomerOrder(customerOrder);
        System.out.println("Order header created successfully");

        List<CartProduct> cartProductList = cartProductService.getCartProduct(cartId);
        for (CartProduct cartProduct : cartProductList) {
            Optional<Product> product = productService.getProductbyId(cartProduct.getProduct().getProductId());
            OrderLine orderLine = new OrderLine();
            orderLine.setCustomerOrder(customerOrder);
            orderLine.setProduct(product.get());
            orderLine.setPrice(product.get().getPrice());
            orderLine.setQuantity(cartProduct.getQuantity());
            orderLineService.saveOrderLine(orderLine);
            System.out.println("Order line created successfully");

            // delete cart product Id
            System.out.println("Delete cart product");
            cartProductService.deleteCartProduct(cartProduct.getCartProductId());
            // reduce quantity
            System.out.println("Update stock");
            int updatedQuantity = product.get().getQuantity() - cartProduct.getQuantity();
            int productId = product.get().getProductId();
            productService.updateQuantity(productId, updatedQuantity);
        }

        return "redirect:/order/" + customerOrder.getOrderId();

    }
    
    /**
     * 
     * @param httpSession
     * @param orderId
     * @return
     */
    @RequestMapping("/order/{orderId}")
    public ModelAndView displayCart(HttpSession httpSession, @PathVariable(name = "orderId") int orderId) {
        ModelAndView mvc = new ModelAndView("order");
        Optional<CustomerOrder> customerOrder = customerOrderService.getCustomerOrderById(orderId);
        List<OrderLine> orderLineList = orderLineService.getOrderLineByOrderId(customerOrder.get().getOrderId());
        double totalPrice = getTotalPrice(orderLineList);
        mvc.addObject("orderLineList", orderLineList);
        mvc.addObject("TotalPrice", totalPrice);
        return mvc;

    }

    private double getTotalPrice(List<OrderLine> orderLineList) {
        double totalPrice = 0;
        for (OrderLine orderLine : orderLineList) {
            totalPrice = totalPrice + orderLine.getPrice();
        }
        return totalPrice;
    }

}
