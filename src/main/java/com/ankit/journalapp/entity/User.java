package com.ankit.journalapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users", indexes = @Index(columnList = "user_name", unique = true, name = "user_name_index"))
public class User {
    @OneToMany
    List<JournalDataModel> journalDataModels;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
}
