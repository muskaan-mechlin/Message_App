package com.example.messageapp.Firebase;

import java.util.Date;

public class ChatMessage {
    private  String receiver;
    private String messageText;
    private String messageUser;
    private String sender;
    private String type;
    private String timestamp;
    private long messageTime;
            String userId;

    public ChatMessage(String messageText, String messageUser,String receiver,String sender,String type,String timestamp,long messageTime,String userId) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.receiver = receiver;
        this.sender = sender;
        this.type = type;
        this.timestamp = timestamp;
        this.messageTime = messageTime;
        this.userId = userId;

        // Initialize to current time
//        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
