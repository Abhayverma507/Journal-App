package com.jouranlApp.journalApp.Scheduler;

import com.jouranlApp.journalApp.Entity.JournalEntry;
import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.cache.AppCache;
import com.jouranlApp.journalApp.enums.Sentiment;
import com.jouranlApp.journalApp.repository.UserRepositoryImpl;
import com.jouranlApp.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

   @Scheduled(cron = "0 0 9 * * SUN")
      public void fetchUserAndSendSaEmail(){
          List<UserEntry> User =userRepository.getUserForSa();
          for(UserEntry user: User){
              List<JournalEntry> journalEntries = user.getJournalEntries();
              List<Sentiment> sentiment = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).toList();
              Map<Sentiment,Integer> sentimentCount = new HashMap<>();
              for(Sentiment Sentiment: sentiment){
                  if(Sentiment!=null){
                       sentimentCount.put(Sentiment,sentimentCount.getOrDefault(Sentiment,0)+1);
                  }
                  Sentiment mostFrequentSentiment=null;
                  int maxCount=0;
                  for (Map.Entry<Sentiment,Integer>entry:sentimentCount.entrySet()){
                      if(entry.getValue()>maxCount){
                          maxCount=entry.getValue();
                          mostFrequentSentiment=entry.getKey();
                      }
                  }
                  if(mostFrequentSentiment !=null){
                      emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days ",mostFrequentSentiment.toString());
                  }
              }
          }
      }
      @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
      appCache.init();
      }

}
