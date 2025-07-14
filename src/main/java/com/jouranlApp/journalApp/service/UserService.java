package com.jouranlApp.journalApp.service;

import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
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
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER"));
        userEntryRepository.save(userEntry);
    }

    public void saveUserAdmin(UserEntry userEntry) {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER","ADMIN"));
        userEntryRepository.save(userEntry);
    }

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
