package com.example.messageapp.Firebase;

public class Reciever {
    private  String receiver;

    public Reciever(String receiver) {
        this.receiver = receiver;
    }
    public Reciever(){

    }

    public String getReceiver() {

        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
