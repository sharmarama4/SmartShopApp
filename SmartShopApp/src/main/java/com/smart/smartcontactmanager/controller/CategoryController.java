package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.helper.Message;
import com.smart.smartcontactmanager.models.Category;


import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.service.CategoryService;
import com.smart.smartcontactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
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

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
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


    @GetMapping("user/add-category")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add_category ");//to get the title
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/add_category_form";


    }

    @PostMapping("user/process-category")
    public String processCategory(@ModelAttribute Category category, @RequestParam("categoryImage") MultipartFile file,
                                 HttpSession session) {
        try {
            if (file.isEmpty()) {
                //if the file is empty then try our message
                System.out.println("Files is empty");
            } else {
                //file the file to folder and update the name to contact
                category.setImageUrl(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");

            }
            categoryService.createCategory(category);
            System.out.println("added to database");
            //message success..
            session.setAttribute("message", new Message("Your category is added!! add more", "success"));

        } catch (Exception e) {
            System.out.println("ERROR " + e.getMessage());
            e.printStackTrace();
            //message error
            session.setAttribute("message", new Message("something went wrong!! ", "danger"));

        }

        // return "redirect:/user/show-categories";
        return "category/add_category_form";
    }



    //showing particular product details
    @GetMapping("user/{id}/category")
    public String showProductDetails(@PathVariable("id") long id, Model model) {
        System.out.println("Id :" + id);
        Category category = this.categoryService.getCategoryById(id);
//
        model.addAttribute("title", category.getCategoryName());
        model.addAttribute("category", category);
        //return"redirect:user/show-products/{page}";
        return "category/category_detail";
    }

    //Open update form handler
    @GetMapping("user/update-category/{id}")
    public String updateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("tittle", "Update_Category");
        Category category = this.categoryService.getCategoryById(id);
        model.addAttribute("category", category);

        return "category/update_category";
    }

    //update category
    @RequestMapping(value = "user/category-process/{id}", method = RequestMethod.POST)
    public String updateHandler(@PathVariable("id") long id, @ModelAttribute Category category, @RequestParam("categoryImage") MultipartFile file, Model model, HttpSession session) {

        try {
            //old contact details
            Category oldCategoryDetails = this.categoryService.getCategoryById(category.getId());
            if (!file.isEmpty()) {
//file work
                //rewrite
                //delete old photo
                File deleteFile = new ClassPathResource("static/img").getFile();
                File file1 = new File(deleteFile, oldCategoryDetails.getImageUrl());
                file1.delete();
                //  update new photo;
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
                category.setImageUrl(file.getOriginalFilename());
            } else {
                category.setImageUrl(oldCategoryDetails.getImageUrl());
            }

            this.categoryService.createCategory(category);
            session.setAttribute("message", new Message("Category is updated successfully...", "success"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("CATEGORY NAME :" + category.getCategoryName());
        System.out.println("CATEGORY ID :" + category.getId());
        return "redirect:/user/show-categories";


    }
//delete category
    @GetMapping("user/show/{id}")
    public String showFormForDelete(@PathVariable("id") long id, Model model, HttpSession httpSession) {

        //call delete user methode
        categoryService.deleteCategoryById(id);

        httpSession.setAttribute("message", new Message("Category deleted successfully...", "success"));
        return "redirect:/user/show-categories";

        //show all categories
    }@GetMapping("user/show-categories")
    public String showCategories(Model model) {
        model.addAttribute("title", "show-categories");
        //  //to send list of contacts

       /* List<Category> categories=this.categoryService.getAllCategories();

        model.addAttribute("categories", categories);*/
        //return "category/show_categories";
        return findPaginated(1, model);
    }


    //page/1?sortField=name&sortDir=asc
    @GetMapping("user/pages/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,

                                Model model) {
        int pageSize = 5;
        Page<Category> page = categoryService.findPaginated(pageNo, pageSize);
        List<Category> categories = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("categories", categories);
        return "category/show_categories";

    }
//this is for home page
    @RequestMapping("user/category")
    public String yourProduct(Model model) {
        model.addAttribute("title", "Category page");
        return "category/user_dashboard";
    }

}
