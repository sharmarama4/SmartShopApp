package com.smart.smartcontactmanager.controller;


import com.smart.smartcontactmanager.helper.Message;
import com.smart.smartcontactmanager.models.CartItem;
import com.smart.smartcontactmanager.models.Contact;
import com.smart.smartcontactmanager.models.User;

import com.smart.smartcontactmanager.repository.ContactRepository;
import com.smart.smartcontactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller

/*@RequestMapping("/user")*/
public class UserController {
    @Autowired
    private UserService userService;


    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        //get the user using username(Email)
        System.out.println("USERNAME :" + userName);
        User user = userService.getUserByUserName(userName);
        model.addAttribute("user", user);
        System.out.println("USER " + user);
    }
/*

    @RequestMapping("/index")

    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "My Home page");
      */
/*  String userName = principal.getName();

        System.out.println("USERNAME: " + userName);
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            model.addAttribute("user", user);
            System.out.println("YAY");
        } else {
            model.addAttribute("user", new User());
            System.out.println("Bag of d**ks");
            System.out.println("USER " + user);
        }
        model.addAttribute("user", user);
        System.out.println("USER " + user);*//*

        return "contact/user_dashboard";
    }
*/

   /* @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add_contact ");//to get the title
        model.addAttribute("contact", new Contact());
        return "contact/add_contact_form";
    }*/

  /*  @GetMapping("/add-user")
    public String openAddUserForm(Model model) {
        model.addAttribute("title", "Add_User ");//to get the title
        model.addAttribute("user", new User());
        return "user/add_user_form";
    }*/
/*
   *//* @GetMapping("/add-product")
    public String openAddProductForm(Model model) {
        model.addAttribute("title", "Add_product ");//to get the title
        model.addAttribute("product", new Product());
        return "product/add_product";
    }*//*
    //processing add contact form
*//*
@PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact,Principal principal){
        String name=principal.getName();
       User user= this.userService.getUserByUserName(name);
      contact.setUser(user);//this both line are important
       user.getContacts().add(contact);
    System.out.println("Data "+contact);
    this.userService.save(user);
    System.out.println("added to database");
    return "contact/add_contact_form";
}
}*//*
    //your profile handler
   *//* @GetMapping("/profile")
    public String yourProfile(Model model){
        model.addAttribute("title","Profile page");
        return "user/profile";
    }*//*
    //open settings handeller*/

  /*  @GetMapping("user/users")
    public String viewHomePage(Model model){
        model.addAttribute("title","Users-smart shop manager ");
        model.addAttribute("listUsers",userService.getAllUsers());
        return"user/index";
    }*/
  @GetMapping("user/users")
  public String viewHomePage(Model model){
      model.addAttribute("title","Users-smart shop manager ");


      //model.addAttribute("listUsers",userService.getAllUsers());
      return findPaginated(1,"name","asc",model);
  }

    @GetMapping("user/showNewUsersForm")
    public String showNewUsersForm(Model model) {
        //create model attribute to bind form data
        User user1=new User();
        model.addAttribute("user1", user1);
        return "user/new-user";
    }
    @PostMapping("user/saveUser")
    public String saveUser( @ModelAttribute("user1") User user1,HttpSession httpSession){
        //save user to database

        userService.save(user1);
        System.out.println(user1);
        httpSession.setAttribute("message", new Message("User saved successfully...", "success"));
        return "redirect:/user/users";
    }
    @GetMapping("user/showFormForUpdate/{id}")
      public String showFormForUpdate(@PathVariable("id") int id, Model model) {
        //get user from the service
        User user1=userService.getUserById(id);
        //set user as a model attribute to pre-populate the form
        model.addAttribute("user1", user1);
        return "user/update-user";
    }

    @PostMapping("user/updateUser")
    public String updateUser( @ModelAttribute("user1") User user1,HttpSession httpSession){
        //save user to database

        userService.save(user1);
        System.out.println(user1);
        httpSession.setAttribute("message", new Message("User updated successfully...", "success"));
        return "redirect:/user/users";
    }
    @GetMapping("user/showFormForDelete/{id}")
    public String showFormForDelete(@PathVariable("id") int id,  HttpSession httpSession) {

        //call delete user methode
        userService.deleteUserById(id);

        httpSession.setAttribute("message", new Message("user deleted successfully...", "success"));
        return "redirect:/user/users";
    }
    //page/1?sortField=name&sortDir=asc
    @GetMapping("/page/{pageNo}")
    public String  findPaginated(@PathVariable(value="pageNo")int pageNo,
                                 @RequestParam("sortField")String sortField,
                                 @RequestParam( value ="sortDir",required = false)String sortDir ,
                                 Model model){
        int pageSize=4;
        Page<User> page=userService.findPaginated(pageNo,pageSize,sortField,sortDir);
        List<User> listUsers=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listUsers",listUsers);
        return "user/index";



    }

}