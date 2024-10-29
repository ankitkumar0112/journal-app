package com.ankit.journalapp.repository;

import com.ankit.journalapp.entity.JournalDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalAppRepository extends JpaRepository<JournalDataModel, String> {
}
