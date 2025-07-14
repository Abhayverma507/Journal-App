package com.jouranlApp.journalApp.repository;

import com.jouranlApp.journalApp.Entity.JournalEntry;
import org.apache.catalina.User;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface journalEntryRepository extends JpaRepository<JournalEntry, Long> {


}
