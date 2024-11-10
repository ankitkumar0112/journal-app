package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.JournalDataModel;
import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.exception.DataNotFoundException;
import com.ankit.journalapp.service.JournalAppService;
import com.ankit.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        UserDataModel userDataModel = Optional.ofNullable(userService.findByUserName(userName))
                .orElseThrow(() -> new DataNotFoundException(String.format("User %s not found", userName)));

        List<JournalDataModel> journals = Optional.ofNullable(userDataModel.getJournalDataModels())
                .orElse(Collections.emptySet())
                .stream().toList();

        return journals.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(journals, HttpStatus.OK);
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
        boolean result = journalAppService.addJournal(journalDataModel, userName);
        return result ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
