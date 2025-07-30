package com.jouranlApp.journalApp.service;

import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.repository.UserEntryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public List<UserEntry> getAll() {
        return userEntryRepository.findAll();
    }

    public void saveUser(UserEntry userEntry) {
        userEntryRepository.save(userEntry);
    }

    public void createNewUser(UserEntry userEntry) {
        try {
            userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
            userEntry.setRoles(Arrays.asList("USER"));
            userEntryRepository.save(userEntry);
        }
        catch (Exception e) {
           log.error("ERROR OCCURRED for {} ", userEntry.getUsername());
           // log.warn("FUCK YOU");
            log.info("FUCK YOU");
           log.debug("FUCK YOU");
        }
    }

    public void saveUserAdmin(UserEntry userEntry) {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER","ADMIN"));
        userEntryRepository.save(userEntry);
    }

    @Transactional
    public Optional<UserEntry> getUserEntryById(Integer id) {
        return userEntryRepository.findById(id);

    }

    public void deleteEntryById(Integer id) {
        userEntryRepository.deleteById(id);
    }

    public UserEntry findByUsername(String username) {
        return userEntryRepository.findByUsername(username);

    }
}
