package com.ankit.journalapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "journal_entry", indexes = @Index(columnList = "title"))
public class JournalDataModel {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(name = "title", nullable = false)
    private String title;
    private String content;
    private LocalDateTime dateTime;
}
