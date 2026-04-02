package com.example.demo.services;

import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repositories.JournalEntryRepository;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveNewEntry(JournalEntry entry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            entry.setDate(new Date());
            JournalEntry saved = journalEntryRepository.save(entry);
            user.getJournalEntry().add(saved);
            // user.setUserName(null);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry:",e);
        }
    }

    public List<JournalEntry> getAllDocument() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findDocumentById(String myId) {
        return journalEntryRepository.findById(myId);
    }

    public void deleteDocumentById(String myId, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntry().removeIf(x -> x.getId().toHexString().equals(myId));
        userService.saveNewUser(user);
        journalEntryRepository.deleteById(myId);
    }

    public Optional<JournalEntry> saveNewEntry(String id, JournalEntry updatedEntry) {
        Optional<JournalEntry> existing = journalEntryRepository.findById(id);
        if (existing.isPresent()) {
            JournalEntry entry = existing.get();
            if (updatedEntry.getTitle() != null) {
                entry.setTitle(updatedEntry.getTitle());
            }
            if (updatedEntry.getContent() != null) {
                entry.setContent(updatedEntry.getContent());
            }
            journalEntryRepository.save(entry);
            return Optional.of(entry);
        }
        return Optional.empty();
    }


    public List<JournalEntry> findByUserName(String userName){

        return null;
    }

}
