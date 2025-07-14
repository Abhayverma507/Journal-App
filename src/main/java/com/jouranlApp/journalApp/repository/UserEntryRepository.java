package com.jouranlApp.journalApp.repository;

import com.jouranlApp.journalApp.Entity.UserEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntryRepository extends JpaRepository<UserEntry, Integer> {
    UserEntry findByUsername (String username);
    void deleteByUsername (String username);
}
