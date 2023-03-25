package com.smart.smartcontactmanager;


import com.smart.smartcontactmanager.models.CartItem;
import com.smart.smartcontactmanager.models.Category;
import com.smart.smartcontactmanager.models.Product;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.CartRepository;
import com.smart.smartcontactmanager.repository.UserRepository;
import com.smart.smartcontactmanager.service.CategoryService;
import com.smart.smartcontactmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SmartContactManagerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SmartContactManagerApplication.class, args);
	}
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private  UserRepository userRepository;
	@Override
	public void run(String... args) throws Exception {
		List<Product> products = productService.getAll();
		if (products.size() == 0) {

			Category category1=new Category();

			Category category2=new Category();
			category1.setCategoryName("Fruits");
			category1.setImageUrl("contact.png");
			category1.setDescription("Fruits are nice");

			category2.setCategoryName("Plants");
			category2.setImageUrl("category.png");
			category2.setDescription("Plants are good");
			categoryService.createCategory(category1);
		


			Product product1=new Product();

			product1.setProductName("Apple");
			product1.setImage("plant.jpg");
			product1.setProductDescription("Apples are fresh");
			product1.setPrice(25);


			Product product2=new Product();
			product2.setProductName("Orange");
			product2.setImage("product.png");
			product2.setProductDescription("Oranges are fresh");
			product2.setPrice(20);
			productService.save(product1);
			productService.save(product2);

			/*CartItem cartItem=new CartItem();
			cartItem.setQuantity(3);
			cartRepository.save(cartItem);
			User user=new User();
			user.setName("Kushal");
			userRepository.save(user);*/
		}
	}
	}

