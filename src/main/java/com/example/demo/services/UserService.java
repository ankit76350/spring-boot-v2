package com.example.demo.services;

import com.example.demo.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.UserRepository;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean saveNewUser(User entry) {

        try {
            entry.setPassword(passwordEncoder.encode(entry.getPassword()));
            entry.setRoles(Arrays.asList("USERS"));
            userRepository.save(entry);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public void saveAdmin(User entry) {
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        entry.setRoles(Arrays.asList("USERS", "ADMIN"));
        userRepository.save(entry);
    }

    public void saveUser(User entry) {
        userRepository.save(entry);
    }

    public Optional<User> findDocumentById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public void deleteDocumentById(ObjectId myId) {
        userRepository.deleteById(myId);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteUser(String userName) {
        userRepository.deleteByUserName(userName);
    }

}
