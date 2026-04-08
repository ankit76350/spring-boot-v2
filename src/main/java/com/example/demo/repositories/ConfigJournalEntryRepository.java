package com.example.demo.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.ConfigJournalEntry;


public interface ConfigJournalEntryRepository extends MongoRepository<ConfigJournalEntry,ObjectId>{

}
