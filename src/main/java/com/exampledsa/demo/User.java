package com.exampledsa.demo;

import org.springframework.stereotype.Service;

public class User {
    public String code;
    public String name;
    public String lastname;
    public String cellphone;
    public String address;
    public String city;

    public User(String code, String name, String lastname, String cellphone, String address, String city) {
        this.code = code;
        this.name = name;
        this.lastname = lastname;
        this.cellphone = cellphone;
        this.address = address;
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
