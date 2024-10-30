package com.ankit.journalapp.service;

import com.ankit.journalapp.entity.JournalDataModel;
import com.ankit.journalapp.repository.JournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalAppService {

    JournalAppRepository journalAppRepository;

    @Autowired
    public JournalAppService(JournalAppRepository journalAppRepository) {
        this.journalAppRepository = journalAppRepository;
    }

    public List<JournalDataModel> getAllJournals() {
        return journalAppRepository.findAll();
    }

    public JournalDataModel getJournalById(Long id) {
        return journalAppRepository.findById(String.valueOf(id)).orElse(null);
    }

    public boolean addJournal(JournalDataModel journalDataModel) {
        try {
            journalDataModel.setDateTime(LocalDateTime.now());
            journalAppRepository.saveAndFlush(journalDataModel);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
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
