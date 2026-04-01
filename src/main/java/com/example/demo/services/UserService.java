package com.example.demo.services;

import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.UserRepository;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() { return userRepository.findAll();}

    public void save(User entry) {
        userRepository.save(entry);
    }

    public Optional<User> findDocumentById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public void deleteDocumentById(ObjectId myId) {
        userRepository.deleteById(myId);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}
