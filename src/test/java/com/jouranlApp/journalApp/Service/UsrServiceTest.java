package com.jouranlApp.journalApp.Service;

import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.repository.UserEntryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UsrServiceTest {
    @Autowired
    private UserEntryRepository userEntryRepository;

    @Disabled
    @Test
    public void testFindByUsername() {
        //   assertEquals(4,3+1);
        // assertNotNull(userEntryRepository.findByUsername("Abhay"));
        UserEntry userEntry = userEntryRepository.findByUsername("Abhay");
        System.out.println("Journal Entries: " + userEntry.getJournalEntries());
        assertTrue(!userEntry.getJournalEntries().isEmpty());

    }

    @ParameterizedTest
    @CsvSource({
            "2,2,4",
            "1,1,2",
           // "3,2,4"
    })
    public void testExample(int a , int b , int expected){
              assertEquals(expected ,a +b,"Faild for " +expected);
    }

}
