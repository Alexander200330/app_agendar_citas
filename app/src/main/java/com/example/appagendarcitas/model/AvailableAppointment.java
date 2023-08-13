package com.example.appagendarcitas.model;

public class AvailableAppointment {
    private long id;
    private String date;
    private String time;
    private long doctorId; // ID del doctor asociado a la cita

    public AvailableAppointment(String date, String time, long doctorId) {
        this.date = date;
        this.time = time;
        this.doctorId = doctorId;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public long getDoctorId() {
        return doctorId;
    }
}
