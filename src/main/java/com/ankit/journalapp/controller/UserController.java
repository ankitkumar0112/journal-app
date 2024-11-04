package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDataModel>> getAllUsers() {
        List<UserDataModel> users = service.getAllUsers();
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDataModel> findByUserName(@PathVariable("name") String userName) {
        UserDataModel user = service.findByUserName(userName);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDataModel userDataModel) {
        service.createUser(userDataModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{userName}")
    public ResponseEntity<Boolean> updateUser(@RequestBody UserDataModel userDataModel, @PathVariable("userName") String userName) {
        service.updateUser(userName, userDataModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
