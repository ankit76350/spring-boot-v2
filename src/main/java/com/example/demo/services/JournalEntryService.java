package com.example.demo.services;

import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.JournalEntryRepository;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveNewEntry(JournalEntry entry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            entry.setDate(new Date());
            JournalEntry saved = journalEntryRepository.save(entry);
            user.getJournalEntry().add(saved);
            userService.save(user);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public List<JournalEntry> getAllDocument() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findDocumentById(String myId) {
        return journalEntryRepository.findById(myId);
    }

    public void deleteDocumentById(String myId) {
        journalEntryRepository.deleteById(myId);
    }

}
