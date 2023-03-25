package com.smart.smartcontactmanager.service;

import com.smart.smartcontactmanager.models.Contact;
import com.smart.smartcontactmanager.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    public Page<Contact> findPaginated(int pageNo,int pageSize,String sortField,String sortDirection){
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo-1,pageSize,sort);
        return this.contactRepository.findAll(pageable);
    }


}
