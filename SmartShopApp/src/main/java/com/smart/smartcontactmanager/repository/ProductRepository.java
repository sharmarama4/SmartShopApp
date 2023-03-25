package com.smart.smartcontactmanager.repository;


import com.smart.smartcontactmanager.models.Category;
import com.smart.smartcontactmanager.models.Product;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
   
   // List<Product> findProductByCategory(Category category);

    //paginations..
    @Query("from Product as c where c.category.id =:id")
   /// public List<Product> findProductByCategory(@Param("categoryName") String categoryName);
    //currentPage-page
    //Contact Per page-5
   // public Page<Product> findProductByCategory(@Param("category") Category category, Pageable pageable);

   // Page<Product> findProductByCategory(boolean add, Pageable pageable);
     public Page<Product> findAll(@Param("id") long id, Pageable pageable);

    List<Product> findByProductNameContainingAndCategory(String query, Category category);


@Query(value="Select * from products P where p.product_name like %:keyword% or P.price like %:keyword%",nativeQuery = true)
    List<Product>findByKeyWord(@Param("keyword") String keyword);



    //  public Page<Contact> findContactByUser(@Param("userId") int userId, Pageable pageable);


}
