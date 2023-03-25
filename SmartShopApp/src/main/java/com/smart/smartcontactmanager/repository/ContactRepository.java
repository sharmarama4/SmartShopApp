package com.smart.smartcontactmanager.repository;

import com.smart.smartcontactmanager.models.Contact;
import com.smart.smartcontactmanager.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
    //paginations..
   /* @Query("from Contact as c where c.user.id =:userId")
    //currentPage-page
    //Contact Per page-5
  public Page<Contact> findContactByUser(@Param("userId") int userId, Pageable pageable);*/
    //search
   public List<Contact> findByNameContainingAndUser(String name, User user);





}
