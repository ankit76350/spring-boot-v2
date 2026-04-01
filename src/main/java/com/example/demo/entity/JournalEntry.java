package com.example.demo.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;

@Document(collection = "journal_entries")
@Data
public class JournalEntry {
    @Id // this is uniqie
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private Date date;
}
