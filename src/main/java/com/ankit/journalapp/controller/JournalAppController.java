package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.JournalDataModel;
import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.exception.DataNotFoundException;
import com.ankit.journalapp.service.JournalAppService;
import com.ankit.journalapp.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalAppController {

    private final JournalAppService journalAppService;
    private final UserService userService;

    public JournalAppController(JournalAppService journalAppService, UserService userService) {
        this.journalAppService = journalAppService;
        this.userService = userService;
    }

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalDataModel>> getAllJournalEntriesOfAUser(@PathVariable String userName) {
        String name = StringUtils.trim(userName);
        UserDataModel userDataModel = findUserByUserName(name);
        List<JournalDataModel> journals = Optional.ofNullable(userDataModel.getJournalDataModels())
                .orElse(Collections.emptySet())
                .stream()
                .toList();

        return journals.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(journals);
    }

    @PostMapping
    public ResponseEntity<Void> createJournalEntryForAUser(@RequestBody JournalDataModel journalDataModel) {
        boolean isJournalAdded = journalAppService.addJournal(journalDataModel);
        return isJournalAdded
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("{name}")
    public ResponseEntity<Void> updateJournalForAUser(@RequestBody JournalDataModel journalDataModel,
                                                      @PathVariable("name") String name) {
        boolean isJournalUpdated = journalAppService.updateJournal(name, journalDataModel);
        return isJournalUpdated
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Void> deleteJournalByName(@PathVariable("name") String name) {
        journalAppService.deleteJournal(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private UserDataModel findUserByUserName(String name) {
        return Optional.ofNullable(userService.findByUserName(name))
                .orElseThrow(() -> new DataNotFoundException(String.format("User %s not found", name)));
    }
}
