package com.ankit.journalapp.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
