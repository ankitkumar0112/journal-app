package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.model.UserDomainModel;
import com.ankit.journalapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDomainModel>> getAllUsers() {
        List<UserDomainModel> users = service.getAllUsers();
        return users.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(users);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDataModel> findByUserName(@PathVariable String name) {
        UserDataModel user = service.findByUserName(name);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDataModel userDataModel) {
        service.createUser(userDataModel);
        return ResponseEntity.status(201).build();
    }
}