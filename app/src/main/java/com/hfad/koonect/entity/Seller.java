package com.hfad.koonect.entity;

public class Seller {
    private String email;
    private String phoneNumber;
    private String userName;
    public Seller(String email, String phoneNumber, String userName ){
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;

    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }
}
