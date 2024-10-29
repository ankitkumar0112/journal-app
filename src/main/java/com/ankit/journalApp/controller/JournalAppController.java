package com.ankit.journalApp.controller;

import com.ankit.journalApp.entity.JournalDataModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalAppController {

    @GetMapping
    public List<JournalDataModel> getAll() {
        return null;
    }

    @GetMapping("/id/{id}")
    public JournalDataModel findById(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping
    public boolean addData(@RequestBody JournalDataModel journalDataModel){
        return true;
    }
}
