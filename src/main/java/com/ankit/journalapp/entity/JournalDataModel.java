package com.ankit.journalapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
