package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.JournalDataModel;
import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.service.JournalAppService;
import com.ankit.journalapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalAppController {
    private static final Logger log = LoggerFactory.getLogger(JournalAppController.class);
    private final JournalAppService journalAppService;
    private final UserService userService;

    public JournalAppController(JournalAppService journalAppService, UserService userService) {
        this.journalAppService = journalAppService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<JournalDataModel>> getAllJournalEntriesOfAUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Received request to get journal entries for user: {}", name);
        UserDataModel userDataModel = findUserByUserName(name);
        List<JournalDataModel> journals = Optional.ofNullable(userDataModel.getJournalDataModels())
                .orElse(Collections.emptySet())
                .stream()
                .toList();

        if (journals.isEmpty()) {
            log.error("No journal entries found for user: {}", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Found {} journal entries for user: {}", journals.size(), name);
            return ResponseEntity.ok(journals);
        }
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
        return userService.findByUserName(name);
    }
}
