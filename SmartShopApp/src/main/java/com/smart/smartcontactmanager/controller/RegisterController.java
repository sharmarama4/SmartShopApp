package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.helper.Message;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Messages;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class RegisterController {
   @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value="/signup", method=RequestMethod.GET)
    public String signup(Model model){
        model.addAttribute("title","Register-smart shop manager ");//to get the title
        model.addAttribute("user",new User());
        return "signup";
    }

    /*//handeller for custom login
    @GetMapping("/signin")
    public String customLogin(Model model){
        model.addAttribute("title","Login page");
        return "signin";
    }*/
    //handler for register user
    @RequestMapping(value="/do_register",method = RequestMethod.POST)
    public String registerPage(@Valid @ModelAttribute("user") User user,BindingResult result1, @RequestParam(value="agreement",defaultValue = "false")boolean agreement,
                            Model model, HttpSession session) {

        try {
            if (!agreement) {
                System.out.println("you have not agreed the terms and conditions");
                throw new Exception("you have not agreed the terms and conditions");
            }
            if (result1.hasErrors()) {
                System.out.println("ERROR" + result1.toString());
                model.addAttribute("user", user);
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));//to get the encrypted password
            System.out.println("Agreement " + agreement);
            System.out.println("USER " + user);
            User result = userRepository.save(user);

            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong !!" + e.getMessage(), "alert-danger"));
            return "signup";
        }
    }

    }

