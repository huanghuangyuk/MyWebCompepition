package com.entity;

public class User {
    private String  phonenumber;
    private String password;
    public User(){

    }
    public void User(String phonenumber,String password ){
        this.phonenumber=phonenumber;
        this.password=password;
    }
    public String getPhonenumber(){
        return phonenumber;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
