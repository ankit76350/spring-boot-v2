package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.User;
import com.example.demo.services.JournalEntryService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllers {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<JournalEntry> getAll() {
        return journalEntryService.getAllDocument();
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> findDocumentById(@PathVariable String myId) {
        Optional<JournalEntry> entryFind = journalEntryService.findDocumentById(myId);
        if (entryFind.isPresent()) {
            return new ResponseEntity<>(entryFind.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getUserJournal/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntry();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createNewJournal/{userName}")
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
        try {

            journalEntryService.saveNewEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userName}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable String myId, @PathVariable String userName) {
        journalEntryService.deleteDocumentById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateJournal/{id}/{userName}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable String id, @PathVariable String userName,
            @RequestBody JournalEntry myEntry) {

        Optional<JournalEntry> updated = journalEntryService.saveNewEntry(id, myEntry);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
