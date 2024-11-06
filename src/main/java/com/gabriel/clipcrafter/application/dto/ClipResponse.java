package com.gabriel.clipcrafter.application.dto;

public class ClipResponse {
    private String message;

    public ClipResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
