package com.smart.smartcontactmanager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
  /*  @Autowired
    private UserRepository userRepository;
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        User user=new User();
        user.setName("Rama Sharma");
        user.setEmail("sharmarama1989@outlook.com");
        Contact contact=new Contact();
        user.getContacts().add(contact);
        userRepository.save(user);
        return "working";
    }*/
   // we can do this manier to save the data in database and to check the database
    @RequestMapping("/")
    public String home(Model model){
       // model.addAttribute("title","Home-smart contact manager ");//to get the title
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model model){
   model.addAttribute("title","About-smart contact manager ");//to get the title
        return "about";
    }
}
