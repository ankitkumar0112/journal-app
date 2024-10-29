package com.ankit.journalApp.repository;

import com.ankit.journalApp.entity.JournalDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalAppRepository extends JpaRepository<JournalDataModel, String> {
}
