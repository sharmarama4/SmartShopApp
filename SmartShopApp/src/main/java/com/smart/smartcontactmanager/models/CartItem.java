package com.smart.smartcontactmanager.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
    @Table(name="cart_Items")
    public class CartItem {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private int quantity;
       @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Column(name = "created_date")
        private Date createdAt;
       @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date upDatedAt;
        @ManyToOne
        @JoinColumn(name="product_id")
        private Product product;
        @ManyToOne
        @JoinColumn(name="user_id")
        private User user;

    public CartItem() {
    }

    public CartItem(int quantity, User user, Product product) {
        this.user=user;
        this.quantity=quantity;
        this.product=product;
    }

    public Date getUpDatedAt() {
        return upDatedAt;
    }

    public void setUpDatedAt(Date upDatedAt) {
        this.upDatedAt = upDatedAt;
    }

    public CartItem(Product product, User user) {

        this.product = product;
        this.user = user;
    }
    public CartItem(int quantity, Date createdAt, Product product, User user) {
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.product = product;
        this.user = user;
    }

    public CartItem(User user) {
        this.user=user;
    }


    public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }



    public Product getProduct() {
            return product;
        }

    public void setProduct(Product product) {
       this.product = product;
    }

    public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                ", product=" + product +
                ", user=" + user +
                '}';
    }
}


