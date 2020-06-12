package com.springboot.ecom.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.ecom.model.Cart;
import com.springboot.ecom.model.CartProduct;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.CartProductService;
import com.springboot.ecom.service.CartService;
import com.springboot.ecom.service.ProductService;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartProductService cartProductService;

    @Autowired
    private ProductService productService;
    
    /**
     * 
     * @param productId
     * @param purchQty
     * @param httpSession
     * @return
     */
    @RequestMapping("/cart/{productId}")
    public String createCart(@PathVariable(name = "productId") int productId, @RequestParam(value = "purchQty", required = false) int purchQty, HttpSession httpSession) {

        User currentLoggedInUser = (User) httpSession.getAttribute(UserController.SESSION_ATTRIB_LOGIN_USER);
        System.out.println("Cart Controller");
        System.out.println(currentLoggedInUser.getEmailId());
        Cart cart = cartService.getCartByUser(currentLoggedInUser);
        System.out.println(cart);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(currentLoggedInUser);
            cart = cartService.createCart(cart);
            System.out.println("Cart created successfully");
        }
        Optional<Product> product = productService.getProductbyId(productId);
        CartProduct cartProduct = cartProductService.getCartProductByCartIdAndProductId(cart.getCartId(), productId);
        if (cartProduct == null) {
            cartProduct = new CartProduct();
            cartProduct.setCart(cart);
            cartProduct.setProduct(product.get());
            cartProduct.setQuantity(purchQty);
        } else {
            cartProduct.setQuantity(cartProduct.getQuantity() + purchQty);
        }

        cartProductService.saveCartProduct(cartProduct);
        System.out.println("Product added to the cart successfully");

        return "redirect:/products";

    }
    
    /**
     * 
     * @param httpSession
     * @return
     */
    @RequestMapping("/cart")
    public ModelAndView displayCart(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("cart");
        User currentLoggedInUser = (User) httpSession.getAttribute(UserController.SESSION_ATTRIB_LOGIN_USER);
        Cart cart = cartService.getCartByUser(currentLoggedInUser);

        if (cart != null) {
            List<CartProduct> cartProductList = cartProductService.getCartProduct(cart.getCartId());
            if (!cartProductList.isEmpty()) {
                double totalPrice = getTotalPrice(cartProductList);
                mvc.addObject("cartProductList", cartProductList);
                mvc.addObject("TotalPrice", totalPrice);
                return mvc;
            } else {
                ModelAndView emptyMvc = new ModelAndView("emptycart");
                return emptyMvc;
            }
        } else {
            ModelAndView emptyMvc = new ModelAndView("emptycart");
            return emptyMvc;
        }

    }

    /**
     * 
     * @param cartProductList
     * @return
     */
    private double getTotalPrice(List<CartProduct> cartProductList) {
        double totalPrice = 0;
        for (CartProduct cartProduct : cartProductList) {
            totalPrice = totalPrice + cartProduct.getProduct().getPrice();
        }
        return totalPrice;
    }
}
