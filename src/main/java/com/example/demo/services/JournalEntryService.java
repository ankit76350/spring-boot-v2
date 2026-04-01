package com.example.demo.services;
import com.example.demo.entity.JournalEntry;
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


}
