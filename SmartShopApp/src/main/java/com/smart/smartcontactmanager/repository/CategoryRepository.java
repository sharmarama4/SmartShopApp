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
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select u from Category u where u.categoryName=:categoryName")//to get dynamic value
    public  Category findByCategoryName(@Param("categoryName")String categoryName);


}
