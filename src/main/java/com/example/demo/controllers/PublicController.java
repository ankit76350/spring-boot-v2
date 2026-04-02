package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.entity.User;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>("Good", HttpStatus.OK);
    }

    @PostMapping("/newUser")
    public void createUser(@RequestBody User user) {
        userService.save(user);
    }

}
