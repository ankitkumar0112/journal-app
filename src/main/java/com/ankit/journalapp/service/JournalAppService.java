package com.ankit.journalapp.service;

import com.ankit.journalapp.entity.JournalDataModel;
import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.exception.DataNotFoundException;
import com.ankit.journalapp.repository.JournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalAppService {

    JournalAppRepository journalAppRepository;
    UserService userService;

    @Autowired
    public JournalAppService(JournalAppRepository journalAppRepository, UserService userService) {
        this.journalAppRepository = journalAppRepository;
        this.userService = userService;
    }

    public List<JournalDataModel> getAllJournals() {
        return journalAppRepository.findAll();
    }

    public JournalDataModel getJournalById(Long id) {
        return journalAppRepository.findById(String.valueOf(id)).orElse(null);
    }

    public boolean addJournal(JournalDataModel journalDataModel, String userName) {
        UserDataModel userDataModel = userService.findByUserName(userName);
        if (userDataModel != null) {
            journalDataModel.setDateTime(LocalDateTime.now());
            journalAppRepository.saveAndFlush(journalDataModel);

            userDataModel.getJournalDataModels().add(journalDataModel);
            userService.updateUser(userDataModel, userName);
            return true;
        } else {
            throw new DataNotFoundException(String.format("User %s not found", userName));
        }
    }

    public boolean updateJournal(Long id, JournalDataModel journalDataModel) {
        JournalDataModel model = journalAppRepository.findById(String.valueOf(id)).orElse(null);
        if (model != null) {
            model.setContent(journalDataModel.getContent());
            model.setTitle(journalDataModel.getTitle());
            model.setDateTime(LocalDateTime.now());
            try {
                journalAppRepository.saveAndFlush(model);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public void deleteJournal(Long id) {
        journalAppRepository.deleteById(String.valueOf(id));
    }
}
