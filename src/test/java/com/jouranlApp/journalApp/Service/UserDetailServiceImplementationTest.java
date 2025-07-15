package com.jouranlApp.journalApp.Service;

import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.repository.UserEntryRepository;
import com.jouranlApp.journalApp.service.UserDetailServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDetailServiceImplementationTest {

    @Mock
    private UserEntryRepository userEntryRepository;

    @InjectMocks
    private UserDetailServiceImplementation userDetailServiceImplementation;

    @Test
    void testLoadUserByUsername() {
        UserEntry userEntry = new UserEntry();
        userEntry.setUsername("Abhay");
        userEntry.setPassword("encodedPassword");
        userEntry.setRoles(List.of("USER"));

        when(userEntryRepository.findByUsername("Abhay")).thenReturn(userEntry);

        UserDetails userDetails = userDetailServiceImplementation.loadUserByUsername("Abhay");

        Assertions.assertEquals("Abhay", userDetails.getUsername());
        Assertions.assertEquals("encodedPassword", userDetails.getPassword());
    }
}


