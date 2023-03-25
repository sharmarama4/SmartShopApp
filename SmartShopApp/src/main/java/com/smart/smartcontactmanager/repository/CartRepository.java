package com.smart.smartcontactmanager.repository;

import com.smart.smartcontactmanager.models.CartItem;
import com.smart.smartcontactmanager.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long> {

   /* @Query("select p from Product p where p.pId= :pId"+"AND select c from CartItem c where c.id= :id ")
    Page<CartItem> findAll(@Param("pId") long pId,@Param("id") long id, Pageable pageable);*/
   /*  List<CartItem> findByUser(User User);*/
   List<CartItem> findByUser(User User);
    @Query("select p from Product p where p.pId= :pId")
    Page<CartItem> findAll(@Param("pId") long pId, Pageable pageable);






  /*  @Query("UPDATE CartItem c SET c.quantity= ?1 WHERE c.product.pId= ?2"+"AND c.user.id= ?3")
    @Modifying
public void updateQuantity(int quantity,long pId, int id);
*/

}
