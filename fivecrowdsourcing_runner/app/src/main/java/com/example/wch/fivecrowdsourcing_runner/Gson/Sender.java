package com.example.wch.fivecrowdsourcing_runner.Gson;

import org.litepal.crud.DataSupport;

public class Sender extends DataSupport{
    private String senderId;
    private String senderName;

    public Sender(String senderId, String senderName) {
        this.senderId = senderId;
        this.senderName = senderName;
    }


    public String getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

}