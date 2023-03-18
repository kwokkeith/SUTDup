package com.application.sutdup.Library.ui;

public class UserData {

    private String name;
    private String password;
    private String phone;


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

    UserData(){}

    UserData(String name , String password, String phone){
        this.name = name;
        this.password = password;
        this.phone = phone;
    }
}
