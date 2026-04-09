package com.example.demo.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// import com.example.demo.enum.Sentiment; // WRONG: 'enum' is a reserved keyword — package won't compile
import com.example.demo.enums.Sentiment; // FIXED: use 'enums' (plural) package

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id // this is uniqie
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private Date date;
    private Sentiment sentiment;
}
