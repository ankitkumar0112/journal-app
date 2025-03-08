package com.ankit.journalapp.model;

import com.ankit.journalapp.entity.JournalDataModel;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDomainModel {
    private UUID id;
    private String userName;
    private List<String> roles;
    private Set<JournalDataModel> journalDataModels;
}
