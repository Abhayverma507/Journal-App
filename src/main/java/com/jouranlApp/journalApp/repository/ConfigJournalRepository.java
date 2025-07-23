package com.jouranlApp.journalApp.repository;

import com.jouranlApp.journalApp.Entity.ConfigJournalAppEntity;
import com.jouranlApp.journalApp.Entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigJournalRepository extends JpaRepository<ConfigJournalAppEntity, Integer> {

}
