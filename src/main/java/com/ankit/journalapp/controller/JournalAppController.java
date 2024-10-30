package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.JournalDataModel;
import com.ankit.journalapp.service.JournalAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalAppController {

    JournalAppService service;

    @Autowired
    public JournalAppController(JournalAppService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<JournalDataModel>> getAll() {
        List<JournalDataModel> allJournals = service.getAllJournals();
        if (!allJournals.isEmpty()) {
            return new ResponseEntity<>(allJournals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalDataModel> findById(@PathVariable("id") Long id) {
        JournalDataModel journal = service.getJournalById(id);
        if (journal != null) {
            return new ResponseEntity<>(journal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Boolean> addJournal(@RequestBody JournalDataModel journalDataModel) {
        boolean result = service.addJournal(journalDataModel);
        return result ? new ResponseEntity<>(true, HttpStatus.CREATED) : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<Boolean> updateJournal(@RequestBody JournalDataModel journalDataModel, @PathVariable("id") Long id) {
        boolean result = service.updateJournal(id, journalDataModel);
        return result ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        service.deleteJournal(id);
        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }
}
