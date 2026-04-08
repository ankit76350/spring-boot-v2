package com.example.demo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "config_journal_app")
public class ConfigJournalEntry {

    @Id
    private ObjectId id;

    private String key;
    private String value;
}
