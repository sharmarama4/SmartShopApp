package com.smart.smartcontactmanager.service;

import com.smart.smartcontactmanager.models.CartItem;
import com.smart.smartcontactmanager.models.Category;
import com.smart.smartcontactmanager.models.Product;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.CartRepository;
import com.smart.smartcontactmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartRepository cartRepository;


    public CartItem getCartItemByID(long id) {
        Optional optional = cartRepository.findById(id);

        if (optional.isPresent()){
            return (CartItem) optional.get();
        }

        return null;
    }


    public CartItem saveCartItem(CartItem cartItem) {
        Optional<CartItem> optionalDbCartItem = cartRepository.findById(cartItem.getId());
        if (optionalDbCartItem.isEmpty()) {
            return null;
        }
        CartItem dbCartItem =optionalDbCartItem.get();
        dbCartItem.setQuantity(cartItem.getQuantity());
        dbCartItem.setProduct(cartItem.getProduct());
        dbCartItem.setUpDatedAt(cartItem.getUpDatedAt());
        dbCartItem.setUser(cartItem.getUser());
        return cartRepository.save(dbCartItem);


    }


    /*public CartItem save(CartItem cartItem){
        if(cartItem.getId()==null){
            cartItem.setCreatedAt(cartItem.getCreatedAt());
        }
        cartItem.setUpDatedAt(cartItem.getUpDatedAt());
        return cartRepository.save(cartItem);
    }
    public void deleteCartItem(long id) {

        cartRepository.deleteById(id);
    }*/
    public Page<CartItem> findPaginated(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);//page no start with 0 base
        return this.cartRepository.findAll(pageable);
    }


}
