package com.smart.smartcontactmanager.repository;

import com.smart.smartcontactmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.email= :email")//to get dynamic value
    public User getUserByUserName(@Param("email")String email);
}
