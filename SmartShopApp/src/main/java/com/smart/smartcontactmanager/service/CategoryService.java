package com.smart.smartcontactmanager.service;

import com.smart.smartcontactmanager.models.Category;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class CategoryService {
        private CategoryRepository categoryRepository;
   @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category) {
            categoryRepository.save(category);
        }

        public List <Category> getAllCategories() {
            return categoryRepository.findAll();

        }
        public Category getCategoryById(long id) {
            Optional optional=categoryRepository.findById(id);
            if (optional.isPresent()){
                return(Category)optional.get();

            }
            return null;
        }


        public void deleteCategoryById(long id) {
            categoryRepository.deleteById(id);
        }

        public Category getCategoryByName(String categoryName) {

            return categoryRepository.findByCategoryName(categoryName);

        }
    public Page<Category> findPaginated(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);//page no start with 0 base
        return this.categoryRepository.findAll(pageable);
    }

    }


