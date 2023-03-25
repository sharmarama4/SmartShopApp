package com.smart.smartcontactmanager.models;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "category_name")
    private  String categoryName;
    @Column( length = 5000)
    private  String description;

    @Column(name = "image_url")
    private String imageUrl;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "category")
    private List<Product>products =new ArrayList<>();

    public Category() {

    }

    public Category( String categoryName, String description, String imageUrl, List<Product> products) {

        this.categoryName = categoryName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.products = products;
    }



    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }


    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setId(long id) {
        this.id = id;
    }




    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
