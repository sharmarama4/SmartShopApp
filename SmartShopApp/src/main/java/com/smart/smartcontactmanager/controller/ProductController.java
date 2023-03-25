package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.helper.Message;
import com.smart.smartcontactmanager.models.Category;
import com.smart.smartcontactmanager.models.Contact;
import com.smart.smartcontactmanager.models.Product;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.ProductRepository;
import com.smart.smartcontactmanager.service.CategoryService;
import com.smart.smartcontactmanager.service.ProductService;
import com.smart.smartcontactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class ProductController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;


    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        //get the user using username(Email)
        System.out.println("USERNAME :" + userName);
        User user = userService.getUserByUserName(userName);
        model.addAttribute("user", user);
        System.out.println("USER " + user);
    }

    @GetMapping("user/add-product/{id}")
    public String seeProductsByCategory(Model model, Product product, @PathVariable("id") long id) {
        Category category = this.categoryService.getCategoryById(id);
        product.setCategory(category);

        model.addAttribute("category", category);
        model.addAttribute("products", new Product("", category));
        return "product/add_product";
    }

    @PostMapping("user/process-product/{id}")
    public String saveProduct(@PathVariable("id") long id, @ModelAttribute Product product, @RequestParam("productImage") MultipartFile file,
                              HttpSession session) {
        try {
            Category category = this.categoryService.getCategoryById(id);
            System.out.println("Category:" + category);
            //

            if (file.isEmpty()) {
                //if the file is empty then try our message
                System.out.println("Files is empty");

            } else {
                //file the file to folder and update the name to contact
                product.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
            }

            product.setCategory(category);
            category.getProducts().add(product);

            productRepository.save(product);
            this.categoryService.createCategory(category);

            System.out.println("Added to database :" + product);
            //mesage success..
            session.setAttribute("message", new Message("Your product is added!! add more", "success"));

        } catch (Exception e) {
            System.out.println("ERROR " + e.getMessage());
            e.printStackTrace();
            //message error
            session.setAttribute("message", new Message("something went wrong!! ", "danger"));

        }
        // return "redirect:/user/add-product/"+id;
        return "redirect:/user/add-product/" + id;
    }

    @GetMapping("user/show-products/{id}/{page}")
    public String showProducts(@PathVariable("id") long id, Model model, @PathVariable("page") Integer page,String keyword) {
        model.addAttribute("title", "show-products");
        //  //to send list of contacts

        Category category = this.categoryService.getCategoryById(id);

        // List<Product> products=this.productRepository.findProductByCategory(category1.getCategoryName());
        //currentpage-page
        //contact per page-5
        Pageable pageable = PageRequest.of(page, 5);

        Page<Product> products = this.productRepository.findAll(category.getId(), pageable);
        model.addAttribute("category", category);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        if (keyword!=null){
           model.addAttribute("products",productService.findByKeyWord(keyword));
        }else {
            model.addAttribute("products", products);
        }
        return "product/show_products";
    }

    //showing particular product details
    @GetMapping("user/{pId}/product")
    public String showProductDetails(@PathVariable("pId") Long pId, Model model, Category category) {
        System.out.println("PID :" + pId);
        Optional<Product> productOptional = this.productRepository.findById(pId);
        Product product = productOptional.get();

        model.addAttribute("title", product.getProductName());
        model.addAttribute("category", category);

        model.addAttribute("product", product);
        return "product/product_detail";
    }
    //delete product handler


    //Open update form handler
    @GetMapping("user/update-product/{pId}")
    public String updateForm(@PathVariable("pId") Long pId, Model model, Category category) {
        model.addAttribute("tittle", "Update_Product");
        Product product = this.productRepository.findById(pId).get();
        model.addAttribute("category", category);
        model.addAttribute("product", product);
        return "product/update_form";
    }


    //update contact handler
    @RequestMapping(value = "user/product-update/{id}", method = RequestMethod.POST)
    public String updateHandler(@PathVariable("id") long id, @ModelAttribute Product product, @RequestParam("productImage") MultipartFile file,  HttpSession session) {

        try {
            Product oldProductDetails = this.productRepository.findById(product.getpId()).get();

            //old contact details


            if (!file.isEmpty()) {
//file work
                //rewrite
                //delete old photo
                File deleteFile = new ClassPathResource("static/img").getFile();
                File file1 = new File(deleteFile, oldProductDetails.getImage());
                file1.delete();
                //  update new photo;
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
                product.setImage(file.getOriginalFilename());
            } else {
                product.setImage(oldProductDetails.getImage());
            }
            //to get the categoryid
            Category category = this.categoryService.getCategoryById(id);

            product.setCategory(category);

            this.productRepository.save(product);

            session.setAttribute("message", new Message("Product is updated successfully...", "success"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("PRODUCT NAME :" + product.getProductName());
        System.out.println("PRODUCT ID :" + product.getpId());

        //return"redirect:/user/"+product.getpId()+"/product";
        return "redirect:/user/show-products/" + id + "/0";
    }

    @GetMapping("user/delete/{id}/{pId}")
    public String deleteProduct(@PathVariable("pId") Long pId, Model model, @PathVariable("id") long id, Category category, HttpSession session) {
        Optional<Product> productOptional = this.productRepository.findById(pId);
        Product product = productOptional.get();
        //check ..Assignment
        this.productRepository.delete(product);
        session.setAttribute("message", new Message("Your product deleted successfully...", "success"));
        return "redirect:/user/show-products/" + category.getId() + "/0";


    }

    //this for common data
    @GetMapping("user/product/{id}")
    public String yourProduct(Model model, @PathVariable("id") long id) {
        model.addAttribute("title", "Product page");
        Category category = categoryService.getCategoryById(id);
        System.out.println("Category :" + category);
        model.addAttribute("category", category);
        return "product/user_dashboardProduct";


    }
//this for sidebar
    @RequestMapping("user/prod")

    public String yourProduct(Model model) {
        model.addAttribute("title", "Product2 page");
        return "product/show_products";
    }
}





