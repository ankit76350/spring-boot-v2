package com.example.demo.services;
import com.example.demo.entity.JournalEntry;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.JournalEntryRepository;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;


    public void saveNewEntry(JournalEntry entry) {
        journalEntryRepository.save(entry);
    }


    public List<JournalEntry> getAllDocument() {
        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> findDocumentById(String myId){
        return journalEntryRepository.findById(myId);
    }
    

    public void deleteDocumentById(String myId){
        journalEntryRepository.deleteById(myId);
    }

    

}
