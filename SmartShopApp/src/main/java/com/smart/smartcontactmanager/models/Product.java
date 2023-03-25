package com.smart.smartcontactmanager.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private long pId;

    private  String productName;
    private String image;
    private  double price;
    @Column(length = 5000)
    private  String productDescription;


    // Many to one relationship
    @JsonIgnore

    @ManyToOne

    private Category category;


    public Product() {
    }



    public Product(String productName, Category category) {

        this.productName = productName;

        this.category = category;
    }

    public Product( String productName, String image, double price, String productDescription, Category category) {

        this.productName = productName;
        this.image = image;
        this.price = price;
        this.productDescription = productDescription;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
       this.productDescription = productDescription;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pId=" + pId +
                ", productName='" + productName + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", productDescription='" + productDescription + '\'' +
                ", category=" + category +
                '}';
    }
}
