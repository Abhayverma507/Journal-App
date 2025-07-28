package com.jouranlApp.journalApp.repository;

import com.jouranlApp.journalApp.Entity.UserEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntryRepository extends JpaRepository<UserEntry, Integer> {
    UserEntry findByUsername (String username);
    void deleteByUsername (String username);

    @Query(value = "SELECT * FROM user_entries WHERE email REGEXP :pattern AND sentiment_analysis = true", nativeQuery = true)
    List<UserEntry> findByEmailRegexAndSentimentAnalysis(@Param("pattern") String pattern);
}
