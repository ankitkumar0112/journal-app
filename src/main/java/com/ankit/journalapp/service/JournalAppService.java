package com.ankit.journalapp.service;

import com.ankit.journalapp.entity.JournalDataModel;
import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.exception.DataNotFoundException;
import com.ankit.journalapp.repository.JournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JournalAppService {

    private final JournalAppRepository journalAppRepository;
    private final UserService userService;

    @Autowired
    public JournalAppService(JournalAppRepository journalAppRepository, UserService userService) {
        this.journalAppRepository = journalAppRepository;
        this.userService = userService;
    }

    public List<JournalDataModel> getAllJournals() {
        return journalAppRepository.findAll();
    }

    public JournalDataModel getJournalById(Long id) {
        return journalAppRepository.findById(String.valueOf(id)).orElseThrow(() ->
                new DataNotFoundException("Journal not found with id: " + id));
    }

    public boolean addJournal(JournalDataModel journalDataModel) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDataModel userDataModel = getUserByUserName(userName);

        journalDataModel.setDateTime(LocalDateTime.now());
        journalAppRepository.saveAndFlush(journalDataModel);

        userDataModel.getJournalDataModels().add(journalDataModel);
        userService.assignJournalToAUser(userDataModel);

        return true;
    }

    public boolean updateJournal(String name, JournalDataModel journalDataModel) {
        UserDataModel userDataModel = getUserByUserName(name);

        Optional<JournalDataModel> optionalJournal = userDataModel.getJournalDataModels().stream().findFirst();
        if (optionalJournal.isPresent()) {
            JournalDataModel existingJournal = optionalJournal.get();
            existingJournal.setContent(journalDataModel.getContent());
            existingJournal.setTitle(journalDataModel.getTitle());
            existingJournal.setDateTime(LocalDateTime.now());
            journalAppRepository.saveAndFlush(existingJournal);
            return true;
        } else {
            throw new DataNotFoundException("No journal found for user: " + name);
        }
    }

    public void deleteJournal(String name) {
        UserDataModel userDataModel = getUserByUserName(name);

        Optional<JournalDataModel> optionalJournal = userDataModel.getJournalDataModels().stream().findFirst();
        if (optionalJournal.isPresent()) {
            UUID journalId = optionalJournal.get().getId();
            journalAppRepository.deleteById(String.valueOf(journalId));
        } else {
            throw new DataNotFoundException("No journal found to delete for user: " + name);
        }
    }

    private UserDataModel getUserByUserName(String name) {
        return Optional.ofNullable(userService.findByUserName(name))
                .orElseThrow(() -> new DataNotFoundException("User " + name + " not found"));
    }
}
