package com.jouranlApp.journalApp.Controller;

import com.jouranlApp.journalApp.ApiResponce.WeatherResponse;
import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.repository.UserEntryRepository;
import com.jouranlApp.journalApp.service.UserService;

import com.jouranlApp.journalApp.service.WeatherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private UserService Service;

    @Autowired
    private UserEntryRepository userEntryRepository;
    @Autowired
    private WeatherService weatherService;

    @PutMapping()
    public ResponseEntity<?> findByUsernameForUpdate(@RequestBody UserEntry userEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry old = Service.findByUsername(username);
          old.setUsername(userEntry.getUsername());
          old.setPassword(userEntry.getPassword());
          old.setRoles(userEntry.getRoles());
        old.setEmail(userEntry.getEmail());
        Service.createNewUser(old);
        return new ResponseEntity<>(old, HttpStatus.OK);

    }

    @Transactional
    @DeleteMapping()
    public ResponseEntity<?> DeleteByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Transactional
    @GetMapping()
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("mumbai");
        String greeting = "";
        if (weatherResponse != null) {
            greeting="Weather feels like "+weatherResponse.getCurrent().getFeelslike();
        }
            return new ResponseEntity<>("Hi " + authentication.getName()+" "+ greeting, HttpStatus.OK);

    }
}
