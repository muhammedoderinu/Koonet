package com.hfad.koonect.entity;

public class Seller {
    private String email;
    private String phoneNumber;
    private String userName;
    private String Id;

    public Seller(){

    }
    public Seller(String email, String phoneNumber, String userName,String Id ){
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.Id = Id;

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

    public String getId(){
        return Id;
    }
}
