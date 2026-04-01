package com.example.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,String>{

    
}