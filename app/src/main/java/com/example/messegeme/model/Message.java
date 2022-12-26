package com.example.messegeme.model;

public class Message {
    private String text;
    private String sendId;
    private String recievedId;

    public Message(String text, String sendId, String recievedId) {
        this.text = text;
        this.sendId = sendId;
        this.recievedId = recievedId;
    }

    public Message() {
    }

    public String getText() {
        return text;
    }

    public String getSendId() {
        return sendId;
    }

    public String getRecievedId() {
        return recievedId;
    }
}
