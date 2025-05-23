package com.ankit.journalapp.service;

import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.exception.AlreadyExistsException;
import com.ankit.journalapp.exception.DataNotFoundException;
import com.ankit.journalapp.model.UserDomainModel;
import com.ankit.journalapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDomainModel> getAllUsers() {
        List<UserDataModel> users = userRepository.findAll();
        return users.stream()
                .map(userDataModel ->
                        UserDomainModel.builder()
                                .id(userDataModel.getId())
                                .userName(userDataModel.getUserName())
                                .roles(userDataModel.getRoles())
                                .journalDataModels(userDataModel.getJournalDataModels())
                                .build())
                .toList();
    }

    public UserDataModel findByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName))
                .orElseThrow(() -> {
                    log.error("User {} not found", userName);
                    return new DataNotFoundException(String.format("User %s not found", userName));
                });
    }

    public void createUser(UserDataModel userDataModel) {
        if (userRepository.findByUserName(userDataModel.getUserName()) != null) {
            throw new AlreadyExistsException("User: " + userDataModel.getUserName() + " already exists");
        }
        userDataModel.setPassword(passwordEncoder.encode(userDataModel.getPassword()));
        userRepository.save(userDataModel);
    }

    public void assignJournalToAUser(UserDataModel userDataModel) {
        userRepository.saveAndFlush(userDataModel);
    }

    public void updateUser(UserDataModel userDataModel) {
        UserDataModel model = findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.setUserName(userDataModel.getUserName());
        model.setPassword(passwordEncoder.encode(userDataModel.getPassword()));
        userRepository.save(model);
    }

    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }
}