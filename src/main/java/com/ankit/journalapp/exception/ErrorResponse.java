package com.ankit.journalapp.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private int statusCode;
    private String message;
}