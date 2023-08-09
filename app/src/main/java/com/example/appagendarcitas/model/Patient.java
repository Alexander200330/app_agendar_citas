package com.example.appagendarcitas.model;

public class Patient extends User {

    private String weight;
    private String height;
    private String blood;

    public Patient(String name, String email, String address, String phoneNumber, String password, String birthday, String sex, String weight, String height, String blood) {
        super(name, email, address, phoneNumber, password, birthday, sex);
        this.weight = weight;
        this.height = height;
        this.blood = blood;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
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
