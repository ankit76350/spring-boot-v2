package com.example.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.services.UserService;


@RestController
@RequestMapping("/user")
public class UserControllers {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAll();
    }


    @PostMapping("/newUser")
    public void createUser(@RequestBody User user){
        userService.save(user);
    }


    @PutMapping("/updateUser/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User userInDb = userService.findByUserName(userName);

        if (userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.save(userInDb);
        }
 
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
