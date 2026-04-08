package com.example.demo.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
// import java.util.stream.Collector; // WRONG: Collector is an interface, it does not have static factory methods like toList()
import java.util.stream.Collectors; // FIXED: Collectors (plural) is the utility class that provides toList(), toSet(), etc.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.cache.AppCache;
import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepositoryImpl;
import com.example.demo.services.EmailService;
import com.example.demo.services.SentimentAnalysisService;

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



    @Scheduled(cron= "0 0 9 * * SUN")
    // @Scheduled(cron= "0 * * ? * *")
    public void fetchUsersAndSendSaMail(){
        List<User> users = userRepository.getUserForSA();

        for(User user:users){
            List<JournalEntry> journalEntries = user.getJournalEntry();
            // List<String> filteEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x-> x.getContent()).collect(Collector.toList());
            // WRONG (two issues):
            //   1. x.getDate() returns java.util.Date, which has NO isAfter() method — that belongs to LocalDateTime
            //   2. Collector.toList() is wrong — Collector is just an interface; the factory method lives on Collectors (plural)
            // FIXED: converted java.util.Date -> LocalDateTime via toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            // FIXED: Collectors.toList() (plural) instead of Collector.toList()
            List<String> filteEntries = journalEntries.stream()
                .filter(x -> x.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                    .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                .map(x -> x.getContent())
                .collect(Collectors.toList());
            String entry = String.join(" ", filteEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
        }

    }


    public void clearAppCache(){
        appCache.init();
    }
}
