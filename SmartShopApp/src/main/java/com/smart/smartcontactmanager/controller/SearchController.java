package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.models.Category;
import com.smart.smartcontactmanager.models.Contact;
import com.smart.smartcontactmanager.models.Product;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.CategoryRepository;
import com.smart.smartcontactmanager.repository.ContactRepository;
import com.smart.smartcontactmanager.repository.ProductRepository;
import com.smart.smartcontactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class SearchController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
//search handler of contact
    @GetMapping("/search/{query}")
    public ResponseEntity<?>search(@PathVariable("query")String query ,Principal principal) {
        System.out.println(query);
        User user=this.userRepository.getUserByUserName(principal.getName());
        List<Contact> contacts=this.contactRepository.findByNameContainingAndUser(query,user);
        return ResponseEntity.ok(contacts);
    }
    //search handler of product
    @GetMapping("/search/product/{query1}/{categoryName}")
    public ResponseEntity<?>search(@PathVariable("query1")String query1 , @PathVariable("categoryName") String categoryName) {
        System.out.println(query1);
        Category category=this.categoryRepository.findByCategoryName(categoryName);
        List<Product> products=this.productRepository.findByProductNameContainingAndCategory(query1,category);
        return ResponseEntity.ok(products);
    }
}
