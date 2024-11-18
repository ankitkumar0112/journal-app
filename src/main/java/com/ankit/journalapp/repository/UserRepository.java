package com.ankit.journalapp.repository;

import com.ankit.journalapp.entity.UserDataModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserDataModel, UUID> {
    UserDataModel findByUserName(String userName);

    void deleteByUserName(String userName);
}
