package com.example.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.demo.entity.User;

public interface UserRepository extends MongoRepository<User,String>{

    
}