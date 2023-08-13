package com.example.appagendarcitas.model;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String speciality;

    public Doctor(int id, String name, String email, String speciality, String password, String address, String phoneNumber, String birthday, String sex) {
        super(name, email, address, phoneNumber, password, birthday, sex);
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }


    @Override
    public void showDataUser() {
        System.out.println("Doctor: " + getName());
        System.out.println("Speciality: " + speciality);
    }

}
