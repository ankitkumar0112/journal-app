package com.ankit.journalapp.service;

import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.exception.DataNotFoundException;
import com.ankit.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDataModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDataModel findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void createUser(UserDataModel userDataModel) {
        userRepository.save(userDataModel);
    }

    public void updateUser(UserDataModel userDataModel, String userName) {
        UserDataModel model = userRepository.findByUserName(userName);
        if (model != null) {
            model.setUserName(userDataModel.getUserName());
            model.setPassword(userDataModel.getPassword());
            model.setJournalDataModels(userDataModel.getJournalDataModels());
            userRepository.save(model);
        } else
            throw new DataNotFoundException(String.format("User %s not found", userName));
    }
}
