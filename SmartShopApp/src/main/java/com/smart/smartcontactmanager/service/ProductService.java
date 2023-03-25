package com.smart.smartcontactmanager.service;

import com.smart.smartcontactmanager.models.Product;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

   @Autowired
 private ProductRepository productRepository;


   /* public Product getProductByID(long categoryId) {
        Optional optional = productRepository.findProductByCategory(categoryId);

        if (optional.isPresent()){
            return (Product) optional.get();
        }

        return null;
    }

   *//* public List<Product> getProductsByCategory(Category category) {

        return productRepository.findProductByCategory(category);
    }*//*

    public void addProduct(Product product) {

        productRepository.save(product);
    }
    public void deleteProduct(int id) {

        productRepository.deleteById(id);
    }


    public List<Product> findProductByCategory(Integer id) {
        return productRepository.findProductByCategory(id);
    }*/


    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        Optional<Product> optionalDbProduct = productRepository.findById(product.getpId());
        if (optionalDbProduct.isEmpty()) {
            return null;
        }

        Product dbProduct = optionalDbProduct.get();
        dbProduct.setProductName(product.getProductName());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setProductDescription(product.getProductDescription());
       dbProduct.setImage(product.getImage());
       dbProduct.setPrice(product.getPrice());
        return productRepository.save(product);
    }

  /*  public Optional<Product> getById(long id) {
        return productRepository.findById(id);
    }*/
  public Product getById(long pId) {
      Optional optional = productRepository.findById(pId);
      if (optional.isPresent()){
          return (Product) optional.get();
      }

      return null;
  }





//get product by keyword


    public List<Product>findByKeyWord(String keyword){
      return productRepository.findByKeyWord(keyword);
    }
}


