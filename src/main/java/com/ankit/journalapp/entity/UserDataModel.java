package com.ankit.journalapp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", indexes = @Index(columnList = "user_name", unique = true, name = "user_name_index"))
public class UserDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private Set<JournalDataModel> journalDataModels;

    private List<String> roles = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<JournalDataModel> getJournalDataModels() {
        return journalDataModels;
    }

    public void setJournalDataModels(Set<JournalDataModel> journalDataModels) {
        this.journalDataModels = journalDataModels;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
