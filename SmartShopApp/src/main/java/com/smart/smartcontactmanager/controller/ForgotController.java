package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class ForgotController {
    //generating otp of 4 digit
    Random random=new Random();
    @Autowired
    private EmailService emailService;
    //email id form open handler
    @RequestMapping("/forgot")
    public String openEmailForm(){

        return "forgot_password_form";
    }
    @PostMapping("/send-otp")
    public String sentOtp(@RequestParam(value="email",required = false)String email, HttpSession session){
        System.out.println("EMAIL "+email);
        //generating otp of 4 digit
       int otp= random.nextInt(999999);
        System.out.println("OTP :"+otp);
        //write code for otp
      /*  String subject="OTP From SMS";
        String message="OTP:"+otp;
        String to=email;
        boolean flag=this.emailService.sendEmail(subject,message,to);
        if(flag){
            return"verify_otp";

        }else{
            session.setAttribute("message","check your email id!!");
            return"forgot_password_form";


        }*/
       return"verify_otp";
    }

}
