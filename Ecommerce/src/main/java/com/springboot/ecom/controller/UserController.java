package com.springboot.ecom.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.ecom.model.User;
import com.springboot.ecom.service.UserService;

@Controller
public class UserController {

    /**
     * 
     */
    public static final String SESSION_ATTRIB_LOGIN_USER = "LoginUser";

    @Autowired
    private UserService userservice;
    
    /**
     * 
     * @return
     */
    @RequestMapping("/")
    public ModelAndView login() {
        ModelAndView mvc = new ModelAndView("login");
        User user = new User();
        mvc.addObject("user", user);
        return mvc;
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping("/signup")
    public ModelAndView signup() {
        ModelAndView mvc = new ModelAndView("signup");
        User user = new User();
        mvc.addObject("user", user);
        return mvc;

    }
    
    /**
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        if (!userservice.checkUserIfExist(user.getEmailId())) {
            userservice.saveUser(user);
            return "login";
        } else {
            return "User already exist";
        }
    }
    
    /**
     * 
     * @param requestUser
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String checkUser(@ModelAttribute("user") User requestUser, HttpSession httpSession) {
        if (userservice.checkUserIfExist(requestUser.getEmailId())) {
            Optional<User> dbUser = userservice.getUserById(requestUser.getEmailId());
            System.out.println(requestUser.getPassword());
            if (requestUser.getPassword().equals(dbUser.get().getPassword())) {
                httpSession.setAttribute(SESSION_ATTRIB_LOGIN_USER, dbUser.get());
                return "redirect:/products";
            }

            return "login";
        }

        return "login";
    }

    /**
     * 
     * @param session
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/role")
    public ModelAndView changeRole(HttpSession session) {
        ModelAndView mvc = new ModelAndView("vendorregistration");
        User currentLoggedInUser = (User) session.getAttribute(SESSION_ATTRIB_LOGIN_USER);
        String emailId = currentLoggedInUser.getEmailId();
        System.out.println(emailId);
        Optional<User> user = userservice.getUserById(emailId);
        System.out.println(user);
        mvc.addObject("user", user);
        return mvc;
    }
    
    /**
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "/userrole", method = { RequestMethod.GET, RequestMethod.POST })
    public String updateUser(@ModelAttribute("user") User user, HttpSession session) {
        User currentLoggedInUser = (User) session.getAttribute(SESSION_ATTRIB_LOGIN_USER);
        currentLoggedInUser.setUserType(user.getUserType());
        currentLoggedInUser.setBusinessName(user.getBusinessName());
        currentLoggedInUser.setBusinessType(user.getBusinessType());
        currentLoggedInUser.setBusinessPhone(user.getBusinessPhone());
        userservice.saveUser(currentLoggedInUser);
        System.out.println(currentLoggedInUser);
        return "redirect:/sellerproducts";

    }
    
    /**
     * 
     * @param httpSession
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return "redirect:/";
    }

}
