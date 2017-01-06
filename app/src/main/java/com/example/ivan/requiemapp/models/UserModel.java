package com.example.ivan.requiemapp.models;

/**
 * Created by Ivan on 3.1.2017..
 */

public class UserModel {
    private String name, address, email, phone_number;


    public UserModel() {
    }

    public UserModel(String name, String address, String email, String phone_number) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
