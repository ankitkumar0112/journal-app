package com.ankit.journalapp.controller;

import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.exception.DataNotFoundException;
import com.ankit.journalapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private static final String MESSAGE = "message";
    private final UserService service;

    public AdminController(UserService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> updateUser(@RequestBody UserDataModel userDataModel) {
        try {
            log.info("Attempting to update user with username: {}", userDataModel.getUserName());
            service.updateUser(userDataModel);
            log.info("User updated successfully: {}", userDataModel.getUserName());
            return ResponseEntity.ok(Map.of(MESSAGE, "User updated successfully"));
        } catch (DataNotFoundException e) {
            log.error("User not found: {}", userDataModel.getUserName(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(MESSAGE, "User not found"));
        } catch (Exception e) {
            log.error("Error updating user: {}", userDataModel.getUserName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(MESSAGE, "An error occurred"));
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        service.deleteByUserName(userName);
        return ResponseEntity.ok("User deleted successfully");
    }
}
