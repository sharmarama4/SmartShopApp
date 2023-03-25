package com.smart.smartcontactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {

  //handeller for custom login
    @GetMapping("/signin")
        public String customLogin(Model model){
        model.addAttribute("title","Login page");
        return "signin";
}
}
