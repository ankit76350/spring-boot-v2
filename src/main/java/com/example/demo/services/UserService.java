package com.example.demo.services;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.UserRepository;

@Component
public class UserService {
    @Autowired
    private UserRepository UserRepository;


    public void saveNewEntry(User entry) {
        UserRepository.save(entry);
    }


    public List<User> getAllDocument() {
        return UserRepository.findAll();
    }


    public Optional<User> findDocumentById(String myId){
        return UserRepository.findById(myId);
    }
    

    public void deleteDocumentById(String myId){
        UserRepository.deleteById(myId);
    }

    

}
