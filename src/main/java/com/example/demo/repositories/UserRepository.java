package com.example.demo.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.demo.entity.User;

public interface UserRepository extends MongoRepository<User,ObjectId>{
    User findByUserName(String userName);
    void deleteByUserName(String userName);
}