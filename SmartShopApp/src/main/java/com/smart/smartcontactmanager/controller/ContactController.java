package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.helper.Message;
import com.smart.smartcontactmanager.models.Contact;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.ContactRepository;

import com.smart.smartcontactmanager.service.ContactService;
import com.smart.smartcontactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ContactController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactService contactService;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        //get the user using username(Email)
        System.out.println("USERNAME :" + userName);
        User user = userService.getUserByUserName(userName);
        model.addAttribute("user", user);
        System.out.println("USER " + user);
    }
    
    
    
    
    @RequestMapping("user/index")

    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "My Home page");
        return "contact/user_dashboard";
    }

    @GetMapping("user/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add_contact ");//to get the title
        model.addAttribute("contact", new Contact());
        return "contact/add_contact_form";
    }

    /*@PostMapping("user/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
                                 Principal principal, HttpSession session) {
        try {


            String name = principal.getName();
            User user = this.userService.getUserByUserName(name);
            if (file.isEmpty()) {
                //if the file is empty then try our message
                System.out.println("Files is empty");
                // contact.setImage("neha.png");
            } else {
                //file the file to folder and update the name to contact
                contact.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
            }
            contact.setUser(user);//this both line are important
            user.getContacts().add(contact);
            System.out.println("Data " + contact);
            this.userService.save(user);
            System.out.println("added to database");
            //mesage success..
            session.setAttribute("message", new Message("Your contact is added!! add more", "success"));

        } catch (Exception e) {
            System.out.println("ERROR " + e.getMessage());
            e.printStackTrace();
            //message error
            session.setAttribute("message", new Message("something went wrong!! ", "danger"));

        }
        return "contact/add_contact_form";

    }*/
    @PostMapping("user/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
                                 Principal principal, HttpSession session) {
        try {


            String name = principal.getName();
            User user = this.userService.getUserByUserName(name);
            if (file.isEmpty()) {
                //if the file is empty then try our message
                System.out.println("Files is empty");
                // contact.setImage("neha.png");
            } else {
                //file the file to folder and update the name to contact
                contact.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
            }
            contact.setUser(user);//this both line are important
            user.getContacts().add(contact);
            System.out.println("Data " + contact);
            this.userService.save(user);
            System.out.println("added to database");
            //mesage success..
            session.setAttribute("message", new Message("Your contact is added!! add more", "success"));

        } catch (Exception e) {
            System.out.println("ERROR " + e.getMessage());
            e.printStackTrace();
            //message error
            session.setAttribute("message", new Message("something went wrong!! ", "danger"));

        }
        return "contact/add_contact_form";

    }
    //show contact handlller


    @GetMapping("user/show-contacts")
    public String showContacts(Model model){
       // List<Contact>contacts=this.contactRepository.findAll();

        return findPaginated(1,"name","asc",model);

    }

    @GetMapping("user/page/{pageNo}")
    public String  findPaginated(@PathVariable(value="pageNo")int pageNo,
                                 @RequestParam(value = "sortField")String sortField,
                                 @RequestParam( value = "sortDir",required = false)String sortDir ,

                                 Model model) {

        model.addAttribute("title", "show-contacts");
        //  //to send list of contacts

        int pageSize = 5;
        Page<Contact> page;

        page = this.contactService.findPaginated(pageNo,pageSize,sortField,sortDir);
        List<Contact> contacts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("contacts", contacts);
        return "contact/show_contacts";


    }



    //showing particular contact details
    @GetMapping("user/{cId}/contact")
    public String showContactDetails(@PathVariable("cId") Integer cId, Model model, Principal principle) {
        System.out.println("CID :" + cId);
        Optional<Contact> contactOptional = this.contactRepository.findById(cId);
        Contact contact = contactOptional.get();

//
        String userName = principle.getName();

        User user = this.userService.getUserByUserName(userName);
        // contact.setUser(user);
        if (user.getId() == contact.getUser().getId()) {
            model.addAttribute("title", contact.getName());
            model.addAttribute("contact", contact);
        }
        return "contact/contact_detail";
    }
    //delete contact handler

    @GetMapping("user/delete/{cid}")
    public String deleteContact(@PathVariable("cid") Integer cId, Principal principal, Model model, HttpSession session) {
        Optional<Contact> contactOptional = this.contactRepository.findById(cId);
        Contact contact = contactOptional.get();
        //check ..Assignment
        //delete old photo
        User user = this.userService.getUserByUserName(principal.getName());
        user.getContacts().remove(contact);
        System.out.println("Deleted");
        this.userService.save(user);
        //this.contactRepository.delete(contact); this way is also one way to solve this method
        //  contact.setUser(null);//if your contact is not deleted
        //if your image is deleted
        //contact.getImage()
        session.setAttribute("message", new Message("contact deleted successfully...", "success"));
        return "redirect:/user/show-contacts";
    }

    //Open update form handler
    @PostMapping("user/update-contact/{cid}")
    public String updateForm(@PathVariable("cid") Integer cId, Model model) {
        model.addAttribute("tittle", "Update_Contact");
        Contact contact = this.contactRepository.findById(cId).get();
        model.addAttribute("contact", contact);
        return "contact/update_form";
    }

    //update contact handler
    @RequestMapping(value = "user/process-update", method = RequestMethod.POST)
    public String updateHandler(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Model model, HttpSession session, Principal principal) {

        try {
            //old contact details
            Contact oldContactDetails = this.contactRepository.findById(contact.getcId()).get();
            if (!file.isEmpty()) {
//file work
                //rewrite
                //delete old photo
                File deleteFile = new ClassPathResource("static/img").getFile();
                File file1 = new File(deleteFile, oldContactDetails.getImage());
                file1.delete();
                //  update new photo;
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
                contact.setImage(file.getOriginalFilename());
            } else {
                contact.setImage(oldContactDetails.getImage());
            }
            String userName = principal.getName();
            User user = this.userService.getUserByUserName(userName);//to get the userid
            contact.setUser(user);
            this.contactRepository.save(contact);
            session.setAttribute("message", new Message("Contact is updated successfully...", "success"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("CONTACT NAME :" + contact.getName());
        System.out.println("CONTACT ID :" + contact.getcId());

        return "redirect:/user/" + contact.getcId() + "/contact";
    }

      //your profile handler
      @GetMapping("user/profile")
      public String yourProfile(Model model){
          model.addAttribute("title","Profile page");
          return "contact/profile";
      }
    //open settings handler
    @GetMapping("user/settings")
    public String yourSetting(Model model) {
        model.addAttribute("title", "setting page");
        return "contact/settings";
    }
    //change password handler
   @PostMapping("user/change-password")
    public String changePassword(@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword,Principal principal,HttpSession session){
        System.out.println("OLD PASSWORD "+oldPassword);
        System.out.println("NEW PASSWORD "+newPassword);
        String userName=principal.getName();
        User currentUser=this.userService.getUserByUserName(userName);
       System.out.println(currentUser.getPassword());
       if(this.bCryptPasswordEncoder.matches(oldPassword,currentUser.getPassword())){
           //change the password
           currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
           this.userService.save(currentUser);
           session.setAttribute("message", new Message("Your password is  successfully changed...", "success"));

       }else{
           //error
           session.setAttribute("message", new Message("please Enter correct old password!!...", "danger"));
           return "redirect:/user/settings";
       }
        return "redirect:/user/index";
    }

}
