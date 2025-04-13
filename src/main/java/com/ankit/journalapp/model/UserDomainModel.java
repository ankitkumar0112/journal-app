package com.ankit.journalapp.model;

import com.ankit.journalapp.entity.JournalDataModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDomainModel {
    private UUID id;
    private String userName;
    private List<String> roles;
    private Set<JournalDataModel> journalDataModels;
}
