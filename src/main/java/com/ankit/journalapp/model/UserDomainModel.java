package com.ankit.journalapp.model;

import com.ankit.journalapp.entity.JournalDataModel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDomainModel {
    private UUID id;
    private String userName;
    private List<String> roles;
    private Set<JournalDataModel> journalDataModels;

    public UserDomainModel() {
    }

    public UserDomainModel(UUID id, String userName, List<String> roles, Set<JournalDataModel> journalDataModels) {
        this.id = id;
        this.userName = userName;
        this.roles = roles;
        this.journalDataModels = journalDataModels;
    }

    public UserDomainModel(UUID id, String userName, List<String> roles) {
        this.id = id;
        this.userName = userName;
        this.roles = roles;
    }

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Set<JournalDataModel> getJournalDataModels() {
        return journalDataModels;
    }

    public void setJournalDataModels(Set<JournalDataModel> journalDataModels) {
        this.journalDataModels = journalDataModels;
    }
}
