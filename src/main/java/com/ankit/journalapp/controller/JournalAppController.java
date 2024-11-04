package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.JournalDataModel;
import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.service.JournalAppService;
import com.ankit.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalAppController {

    JournalAppService journalAppService;
    UserService userService;

    @Autowired
    public JournalAppController(JournalAppService journalAppService, UserService userService) {
        this.journalAppService = journalAppService;
        this.userService = userService;

    }

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalDataModel>> getAllJournalEntriesOfAUser(@PathVariable String userName) {
        UserDataModel user = userService.findByUserName(userName);
        List<JournalDataModel> allJournals = user.getJournalDataModels().stream().toList();
        if (!allJournals.isEmpty()) {
            return new ResponseEntity<>(allJournals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalDataModel> findById(@PathVariable("id") Long id) {
        JournalDataModel journal = journalAppService.getJournalById(id);
        if (journal != null) {
            return new ResponseEntity<>(journal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<Void> createJournalEntryForAUser(@RequestBody JournalDataModel journalDataModel, @PathVariable String userName) {
        UserDataModel user = userService.findByUserName(userName);
        if (user != null) {
            boolean result = journalAppService.addJournal(journalDataModel);
            user.getJournalDataModels().add(journalDataModel);
            userService.updateUser(userName, user);
            return result ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateJournal(@RequestBody JournalDataModel journalDataModel, @PathVariable("id") Long id) {
        boolean result = journalAppService.updateJournal(id, journalDataModel);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        journalAppService.deleteJournal(id);
        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }
}
