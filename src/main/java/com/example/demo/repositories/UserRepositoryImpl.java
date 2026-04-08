package com.example.demo.repositories;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository // FIX: added so Spring can manage this as a bean and @Autowired works in tests
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<User> getUserForSA(){

        Query query = new Query();
        // query.addCriteria(Criteria.where("userName").is("ankit"));
        query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("sentimentAnalysis").exists(true));
       
        List<User> users = mongoTemplate.find(query, User.class);

        return users;
    }
}
