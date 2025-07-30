package com.jouranlApp.journalApp.Scheduler;

import com.jouranlApp.journalApp.Entity.JournalEntry;
import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.cache.AppCache;
import com.jouranlApp.journalApp.repository.UserRepositoryImpl;
import com.jouranlApp.journalApp.service.EmailService;
import com.jouranlApp.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService  sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
      public void fetchUserAndSendSaEmail(){
          List<UserEntry> User =userRepository.getUserForSa();
          for(UserEntry user: User){
              List<JournalEntry> journalEntries = user.getJournalEntries();
              List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).toList();
              String entry = String.join("",filteredEntries);
              String sentiment=sentimentAnalysisService.getSentiment(entry);
              emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days ",sentiment);
          }
      }
      @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
      appCache.init();
      }

}
