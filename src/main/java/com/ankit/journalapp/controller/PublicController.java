package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class PublicController {

    private final UserService service;

    public PublicController(UserService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserDataModel userDataModel) {
        service.updateUser(userDataModel);
        return ResponseEntity.ok().build();
    }
}
