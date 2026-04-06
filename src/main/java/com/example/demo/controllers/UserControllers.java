package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.response.WeatherResponse;
import com.example.demo.entity.User;
import com.example.demo.services.UserService;
import com.example.demo.services.WeatherService;

@RestController
@RequestMapping("/user")
public class UserControllers {

    @Autowired
    private UserService userService;

    // WRONG: weatherService was used in greeting() but never declared or injected — caused NoSuchBeanDefinitionException
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);

        if (userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveUser(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteUser(userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    // WRONG: @DeleteMapping("/user") — this mapped GET requests from the browser to a DELETE endpoint, causing 405 Method Not Allowed
    @GetMapping("")
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if (weatherResponse != null) {
            greeting = " Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+ userName + greeting,   HttpStatus.OK);
    }

}
