package com.example.appagendarcitas.model;

import com.example.appagendarcitas.User;

public class Patient extends User {

    private String birthday;
    private double weight;
    private double height;
    private String blood;

    public Patient(String name, String email, String address, String phoneNumber, String password, String birthday, double weight, double height, String blood) {
        super(name, email, address, phoneNumber, password);
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.blood = blood;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    @Override
    public void showDataUser() {
        // Implementar si es necesario
    }
}
