package com.mhutshow.daktarlagbe.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Message {

    private String message;
    private Date dateCreated;
    private String userSender;

    public Message() { }

    public Message(String message, String userSender) {
        this.message = message;
        this.userSender = userSender;
    }


    // --- GETTERS ---
    public String getMessage() { return message; }

    @ServerTimestamp
    public Date getDateCreated() { return dateCreated; }
    public String getUserSender() { return userSender; }

    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(String userSender) { this.userSender = userSender; }

}