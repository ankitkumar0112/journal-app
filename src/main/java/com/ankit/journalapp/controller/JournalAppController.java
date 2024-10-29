package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.JournalDataModel;
import com.ankit.journalapp.service.JournalAppService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JournalDataModel> getAll() {
        return service.getAllJournals();
    }

    @GetMapping("/id/{id}")
    public JournalDataModel findById(@PathVariable("id") Long id) {
        return service.getJournalById(id);
    }

    @PostMapping
    public boolean addJournal(@RequestBody JournalDataModel journalDataModel) {
        return service.addData(journalDataModel);
    }

    @PutMapping("{id}")
    public boolean updateJournal(@RequestBody JournalDataModel journalDataModel, @PathVariable("id") Long id) {
        return service.updateJournal(id, journalDataModel);
    }

    @DeleteMapping("{id}")
    public boolean deleteById(@PathVariable("id") Long id) {
        service.deleteJournal(id);
        return true;
    }
}
