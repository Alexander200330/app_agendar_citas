package com.example.appagendarcitas.model;

public class ScheduledAppointment {
    private long id;
    private String date;
    private String time;
    private int doctorId;
    private int patientId;
    private long availableAppointmentId; // This is the ID of the available appointment being scheduled

    public ScheduledAppointment(String date, String time, int doctorId, int patientId) {
        this.date = date;
        this.time = time;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public long getAvailableAppointmentId() {
        return availableAppointmentId;
    }

    public void setAvailableAppointmentId(long availableAppointmentId) {
        this.availableAppointmentId = availableAppointmentId;
    }
}
