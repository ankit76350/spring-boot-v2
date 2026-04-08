package com.example.demo.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.ConfigJournalEntry;
import com.example.demo.repositories.ConfigJournalEntryRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {
    @Autowired
    private ConfigJournalEntryRepository configJournalEntryRepository;

    private Map<String, String> APP_CACHE;

    public Map<String, String> getAppCache() {
        return APP_CACHE;
    }

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalEntry> all = configJournalEntryRepository.findAll();
        for (ConfigJournalEntry configJournalEntry : all) {
            APP_CACHE.put(configJournalEntry.getKey(), configJournalEntry.getValue());
        }
    }
}
