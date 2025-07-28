package com.jouranlApp.journalApp.repository;

import com.jouranlApp.journalApp.Entity.UserEntry;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl {

    @Autowired
    private  UserEntryRepository userEntryRepository;


    public List<UserEntry> getUserForSa(){
        List<UserEntry> users = userEntryRepository.findByEmailRegexAndSentimentAnalysis("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

        return users;
    }
}
