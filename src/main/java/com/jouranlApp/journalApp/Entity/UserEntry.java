package com.jouranlApp.journalApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "user_entries")
@Data
@JsonPropertyOrder({ "id", "username", "password", "roles", "email","sentimentAnalysis", "journalEntries" })
public class UserEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;
    private boolean sentimentAnalysis;

    @Column(nullable = false)
    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles ;
}
