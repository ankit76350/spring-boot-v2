package com.example.demo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.JournalEntry;
import com.example.demo.services.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllers {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/getAll")
    public List<JournalEntry> getAll() {
        return journalEntryService.getAllDocument();
    }

    @PostMapping("/save")
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry myEntry) {
        try {
            myEntry.setDate(new Date());
            journalEntryService.saveNewEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> findDocumentById(@PathVariable String myId) {
        Optional<JournalEntry> entryFind = journalEntryService.findDocumentById(myId);
        if (entryFind.isPresent()) {
            return new ResponseEntity<>(entryFind.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable String myId) {
        journalEntryService.deleteDocumentById(myId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
