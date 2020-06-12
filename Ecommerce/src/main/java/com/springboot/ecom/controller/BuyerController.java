package com.springboot.ecom.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.ecom.model.Product;
import com.springboot.ecom.repository.ProductRepository;

@Controller
public class BuyerController {

    @Autowired
    private ProductRepository productRepository;
    
    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping("/products")
    public String displayProducts(Model model) {

        List<Product> listProducts = productRepository.findAll();
        model.addAttribute("listProducts", listProducts);
        return "products";
    }
    
    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping("/product/{id}")
    public ModelAndView showProduct(@PathVariable(name = "id") int id) {
        ModelAndView mvc = new ModelAndView("show_product");
        Optional<Product> product = productRepository.findById(id);
        mvc.addObject("product", product.get());
        return mvc;

    }

}
