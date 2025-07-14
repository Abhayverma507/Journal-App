package com.jouranlApp.journalApp.service;


import com.jouranlApp.journalApp.Entity.JournalEntry;
import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.repository.journalEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class journalEntryService {
    @Autowired
    private journalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userServices;


    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        UserEntry user = userServices.findByUsername(username);
        journalEntry.setUser(user);
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(journalEntry);

    }

    public ResponseEntity<?> getJournalEntryById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user = userServices.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(id))
                .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @Transactional
    public ResponseEntity<?> deleteEntryById(Long id, String username) {

        try {
            UserEntry userEntry = userServices.findByUsername(username);
            boolean removed = userEntry.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userServices.saveUser(userEntry);
                journalEntryRepository.deleteById(id);
                return ResponseEntity.ok("Journal entry deleted successfully.");
            } else {
                // Journal entry not found in user's list
                return new ResponseEntity<>("Journal entry not found for user", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();// 👈 Log actual error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<JournalEntry> getJournalEntryByIdForUpdate(Long id) {
        return journalEntryRepository.findById(id);

    }

    public void saveUpdatedJournalEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }
}

