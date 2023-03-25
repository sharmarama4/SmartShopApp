package com.smart.smartcontactmanager.service;

import com.smart.smartcontactmanager.models.Category;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

        @Autowired
        private UserRepository userRepository;
        public User save(User user){

            Optional<User> optionalDbUser = userRepository.findById(user.getId());
            if (optionalDbUser.isEmpty()) {
                return null;
            }

            User dbUser = optionalDbUser.get();
            dbUser.setName(user.getName());
            dbUser.setEmail(user.getEmail());
            dbUser.setRole(user.getRole());
            dbUser.setAbout(user.getAbout());
            dbUser.setEnabled(user.isEnabled());

            return userRepository.save(dbUser);
        }

        public User getUserByUserName(String userName) {
            return userRepository.getUserByUserName(userName);
        }
       public User getUserById(int id) {

        Optional optional=userRepository.findById(id);
        if (optional.isPresent()){
            return(User)optional.get();

        }
        return null;
    }

    public List<User> getAllUsers() {

            return userRepository.findAll();
    }
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public Page<User> findPaginated(int pageNo, int pageSize,String sortField,String sortDirection) {
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo-1,pageSize,sort);//page no start with 0 base
        return this.userRepository.findAll(pageable);
    }
}



