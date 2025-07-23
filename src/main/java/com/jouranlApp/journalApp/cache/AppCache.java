package com.jouranlApp.journalApp.cache;

import com.jouranlApp.journalApp.Entity.ConfigJournalAppEntity;
import com.jouranlApp.journalApp.repository.ConfigJournalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalRepository configJournalRepository;

    public Map<String, String> APPCACHE=new HashMap<>();

    @PostConstruct
    public void init() {
     List<ConfigJournalAppEntity> all =configJournalRepository.findAll();
     for (ConfigJournalAppEntity configJournalAppEntity :all){
         APPCACHE.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
     }
    }
}
