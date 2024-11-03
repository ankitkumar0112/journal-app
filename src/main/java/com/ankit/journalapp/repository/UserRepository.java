package com.ankit.journalapp.repository;

import com.ankit.journalapp.entity.UserDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDataModel, UUID> {
    UserDataModel findByUserName(String userName);
}
