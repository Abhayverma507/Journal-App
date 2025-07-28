package com.jouranlApp.journalApp.Repository;

import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.repository.UserEntryRepository;
import com.jouranlApp.journalApp.repository.UserRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    private UserEntryRepository userEntryRepository;
    @Autowired
    private  UserRepositoryImpl userRepository;

    @Test
    public void testFindByUsername() {
        //  List<UserEntry> users = userEntryRepository.findUsername("Abhay");
        List<UserEntry> users = userRepository.getUserForSa();
        // This is safe, because it avoids lazy loading journalEntries
        users.forEach(user -> System.out.println("User: " + user.getId()));
        users.forEach(user -> System.out.println("User: " + user.getUsername()));
        users.forEach(user -> System.out.println("User: " + user.getPassword()));
        users.forEach(user -> System.out.println("User: " + user.getRoles()));
        users.forEach(user -> System.out.println("User: " + user.getEmail()));
     //   users.forEach(user -> System.out.println("User: " + user.getRoles()));

        assertFalse(users.isEmpty());
    }

}
