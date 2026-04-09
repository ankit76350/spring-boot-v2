package com.example.demo.scheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map; // FIXED: Map<Sentiment, Integer> and Map.Entry<> require this import
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.example.demo.cache.AppCache;
import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.User;
// import com.example.demo.enum.Sentiment; // WRONG: 'enum' is a reserved keyword — package won't compile
import com.example.demo.enums.Sentiment; // FIXED: use 'enums' (plural) package
import com.example.demo.repositories.UserRepositoryImpl;
import com.example.demo.services.EmailService;
import com.example.demo.services.SentimentAnalysisService;

@Slf4j
@Component
public class UserSchedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    // @Scheduled(cron= "0 0 9 * * SUN")
    // @Scheduled(cron= "0 * * ? * *")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        // DEBUG: If this logs 0, no users have sentimentAnalysis=true with a valid email in DB
        log.info("SA Scheduler: found {} eligible user(s)", users.size());

        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntry();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                            .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());

            // DEBUG: If this logs 0, either no entries in last 7 days OR all sentiment fields are null
            // NOTE: sentiment field was just added to JournalEntry — existing MongoDB documents
            // won't have it set, so getSentiment() returns null for every old entry.
            // Fix: open MongoDB Compass and set the 'sentiment' field on your journal entries,
            // or save new entries via the API (they will have sentiment populated going forward).
            log.info("User [{}]: {} journal entries in last 7 days, {} with non-null sentiment",
                    user.getUserName(), journalEntries.size(), sentiments.stream().filter(s -> s != null).count());

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;

            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                log.info("Sending sentiment email to [{}]: {}", user.getEmail(), mostFrequentSentiment);
                emailService.sendEmail(
                        user.getEmail(),
                        "Sentiment for last 7 days ",
                        mostFrequentSentiment.toString());
            } else {
                // DEBUG: logs when all sentiments were null or no entries passed the 7-day filter
                log.warn("User [{}]: no sentiment data to send — skipping email", user.getUserName());
            }
        }

    }

    public void clearAppCache() {
        appCache.init();
    }
}
