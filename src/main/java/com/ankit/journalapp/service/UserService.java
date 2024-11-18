package com.ankit.journalapp.service;

import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.exception.DataNotFoundException;
import com.ankit.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDataModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDataModel findByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName))
                .orElseThrow(() -> new DataNotFoundException("User " + userName + " not found"));
    }

    public void createUser(UserDataModel userDataModel) {
        userDataModel.setPassword(passwordEncoder.encode(userDataModel.getPassword()));
        userRepository.save(userDataModel);
    }

    public void updateUser(UserDataModel userDataModel) {
        UserDataModel model = findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.setUserName(userDataModel.getUserName());
        model.setPassword(passwordEncoder.encode(userDataModel.getPassword()));
        model.setJournalDataModels(userDataModel.getJournalDataModels());
        userRepository.save(model);
    }

    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }
}