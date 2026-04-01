package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping
    public List<JournalEntry>getAll(){
        return null;
    }

    @PostMapping("/save")
    public boolean saveEntry(@RequestBody JournalEntry myEntry){
        journalEntryService.saveNewEntry(myEntry);
        return true;
    }
}
