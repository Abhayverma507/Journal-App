package com.jouranlApp.journalApp.Controller;


import com.jouranlApp.journalApp.Entity.JournalEntry;
import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.service.UserService;
import com.jouranlApp.journalApp.service.journalEntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Journal")
public class journalEntryControllerV2 {

    @Autowired
    private journalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntryOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user = userService.findByUsername(username);
        List<JournalEntry> all = user.getJournalEntries();
        if (all == null && all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> CreateEntry(@RequestBody JournalEntry MyEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (MyEntry.getTitle() != null || MyEntry.getTitle().trim().isEmpty()
                || MyEntry.getContent() != null || MyEntry.getContent().trim().isEmpty()) {
            try {
                journalEntryService.saveEntry(MyEntry, username);
                return new ResponseEntity<>(MyEntry, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace(); // 👈 Log actual error
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 if invalid
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable Long myId) {

        return journalEntryService.getJournalEntryById(myId);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return journalEntryService.deleteEntryById(myId, username);
        // return ResponseEntity.ok("Journal entry deleted successfully.");
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry userEntry = userService.findByUsername(username);
        List<JournalEntry> collect = userEntry.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            JournalEntry oldEntry = journalEntryService.getJournalEntryByIdForUpdate(myId).orElse(null);
            if (oldEntry != null) {
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
                oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().trim().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
                journalEntryService.saveUpdatedJournalEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
            return new ResponseEntity<>("Journal entry not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Journal entry not found", HttpStatus.NOT_FOUND);
        }
    }
}
