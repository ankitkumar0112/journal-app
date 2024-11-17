package com.ankit.journalapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users", indexes = @Index(columnList = "user_name", unique = true, name = "user_name_index"))
public class UserDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_name", nullable = false)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Set<JournalDataModel> journalDataModels = new HashSet<>();

    private List<String> roles = new ArrayList<>();
}
