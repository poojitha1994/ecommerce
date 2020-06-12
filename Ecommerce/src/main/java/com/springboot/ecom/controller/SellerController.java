package com.springboot.ecom.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.ProductService;

@Controller
public class SellerController {

    @Autowired
    private ProductService productService;

    /**
     * 
     * @param model
     * @param httpSession
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/sellerproducts")
    public String displayProductsBySeller(Model model, HttpSession httpSession) {
        User currentLoggedInUser = (User) httpSession.getAttribute(UserController.SESSION_ATTRIB_LOGIN_USER);
        String emailId = currentLoggedInUser.getEmailId();
        List<Product> listProducts = productService.getAllProductsByUser(emailId);
        model.addAttribute("listProducts", listProducts);
        return "seller_products";
    }
    
    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping("/newproduct")
    public String displayNewProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "new_product";

    }
    
    /**
     * 
     * @param product
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/sellerproducts";

    }
    
    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable(name = "id") int id) {
        ModelAndView mvc = new ModelAndView("edit_product");
        Optional<Product> product = productService.getProductbyId(id);
        mvc.addObject("product", product);
        return mvc;

    }
    
    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        productService.deleteProduct(id);
        return "redirect:/sellerproducts";

    }

}
