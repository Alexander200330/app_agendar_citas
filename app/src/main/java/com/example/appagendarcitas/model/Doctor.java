package com.example.appagendarcitas.model;

import com.example.appagendarcitas.User;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private String speciality;
    private List<AvailableAppointment> availableAppointments;

    public Doctor(String name, String email, String speciality, String password, String address, String phoneNumber, String birthday, String sex) {
        super(name, email, address, phoneNumber, password, birthday, sex);
        this.speciality = speciality;
        this.availableAppointments = new ArrayList<>();
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public List<AvailableAppointment> getAvailableAppointments() {
        return availableAppointments;
    }

    public void addAvailableAppointment(String date, String time) {
        availableAppointments.add(new AvailableAppointment(date, time));
    }

    @Override
    public String toString() {
        return super.toString() + "\nSpeciality: " + speciality + "\nAvailable: " + availableAppointments.toString();
    }

    @Override
    public void showDataUser() {
        System.out.println("Doctor: " + getName());
        System.out.println("Speciality: " + speciality);
    }

    public static class AvailableAppointment {
        private String date;
        private String time;

        public AvailableAppointment(String date, String time) {
            this.date = date;
            this.time = time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "Available Appointments \nDate: " + date + "\nTime: " + time;
        }
    }
}
