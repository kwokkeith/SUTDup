package com.application.sutdup.Library.ui;

public class UserData {

    private String name,password,phone,telehandle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelehandle() {
        return telehandle;
    }

    public void setTelehandle(String telehandle) {
        this.telehandle = telehandle;
    }

    UserData(){}

    UserData(String name , String password, String phone, String telehandle){
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.telehandle = telehandle;
    }

}

