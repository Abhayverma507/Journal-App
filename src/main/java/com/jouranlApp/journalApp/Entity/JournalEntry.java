package com.jouranlApp.journalApp.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "journal_entries")
@Data
@NoArgsConstructor
@JsonPropertyOrder({ "id", "title", "content", "date", "user" })
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false)
    private String title;
    private String content;
    private LocalDateTime Date;

    @ManyToOne
    @JoinColumn(name= "user_id" , nullable = false)
    @JsonBackReference
    private UserEntry user;
}
