package com.nextuple.pranay.backend.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
    private LocalDateTime timeStamp;
    private String error;
    private String message;
    private String description;

    public ErrorDetails(String error, String message, String description) {
        this.error = error;
        this.message = message;
        this.description = description;
        updateTimeStamp();
    }

    public void updateTimeStamp() {
        this.timeStamp = LocalDateTime.now();
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
