package com.example.appagendarcitas.model;

import java.util.Date;

public class AppointmentDoctor implements ISchedulable {
    private Date date;
    private String time;
    private Doctor doctor;
    private Patient patient;

    public AppointmentDoctor(Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void schedule(Date date, String time) {
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "AppointmentDoctor{" +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", doctor=" + doctor +
                ", patient=" + patient +
                '}';
    }
}
