package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        service.deleteByUserName(userName);
        return ResponseEntity.noContent().build();
    }
}
