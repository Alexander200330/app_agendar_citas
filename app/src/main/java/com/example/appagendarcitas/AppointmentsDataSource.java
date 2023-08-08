package com.example.appagendarcitas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.appagendarcitas.model.Doctor;
import com.example.appagendarcitas.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsDataSource {
    private static final String TAG = "AppointmentsDataSource";
    private SQLiteDatabase database;
    private AppointmentsDatabaseHelper dbHelper;

    public AppointmentsDataSource(Context context) {
        dbHelper = new AppointmentsDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Insertar un doctor en la base de datos
    public long insertDoctor(Doctor doctor) {
        ContentValues values = new ContentValues();
        values.put(AppointmentsDatabaseHelper.COLUMN_NAME, doctor.getName());
        values.put(AppointmentsDatabaseHelper.COLUMN_EMAIL, doctor.getEmail());
        values.put(AppointmentsDatabaseHelper.COLUMN_SPECIALITY, doctor.getSpeciality());
        values.put(AppointmentsDatabaseHelper.COLUMN_PASSWORD, doctor.getPassword());
        values.put(AppointmentsDatabaseHelper.COLUMN_ADDRESS, doctor.getAddress());
        values.put(AppointmentsDatabaseHelper.COLUMN_PHONE_NUMBER, doctor.getPhoneNumber());
        values.put(AppointmentsDatabaseHelper.COLUMN_BIRTHDAY, doctor.getBirthday());
        values.put(AppointmentsDatabaseHelper.COLUMN_SEX, doctor.getSex());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(AppointmentsDatabaseHelper.TABLE_DOCTORS, null, values);
    }

    // Insertar un paciente en la base de datos
    public long insertPatient(Patient patient) {
        ContentValues values = new ContentValues();
        values.put(AppointmentsDatabaseHelper.COLUMN_NAME, patient.getName());
        values.put(AppointmentsDatabaseHelper.COLUMN_EMAIL, patient.getEmail());
        values.put(AppointmentsDatabaseHelper.COLUMN_BIRTHDAY, patient.getBirthday());
        values.put(AppointmentsDatabaseHelper.COLUMN_WEIGHT, patient.getWeight() + " kg");
        values.put(AppointmentsDatabaseHelper.COLUMN_HEIGHT, patient.getHeight() + " cm");
        values.put(AppointmentsDatabaseHelper.COLUMN_BLOOD, patient.getBlood());
        values.put(AppointmentsDatabaseHelper.COLUMN_PASSWORD, patient.getPassword());
        values.put(AppointmentsDatabaseHelper.COLUMN_ADDRESS, patient.getAddress());
        values.put(AppointmentsDatabaseHelper.COLUMN_PHONE_NUMBER, patient.getPhoneNumber());
        values.put(AppointmentsDatabaseHelper.COLUMN_SEX, patient.getSex());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(AppointmentsDatabaseHelper.TABLE_PATIENTS, null, values);
    }

    // Obtener todos los doctores de la base de datos
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        Cursor cursor = database.query(AppointmentsDatabaseHelper.TABLE_DOCTORS,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Doctor doctor = cursorToDoctor(cursor);
            doctors.add(doctor);
            cursor.moveToNext();
        }

        cursor.close();
        return doctors;
    }

    // Obtener todos los pacientes de la base de datos
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        Cursor cursor = database.query(AppointmentsDatabaseHelper.TABLE_PATIENTS,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Patient patient = cursorToPatient(cursor);
            patients.add(patient);
            cursor.moveToNext();
        }

        cursor.close();
        return patients;
    }

    // Convertir el cursor de la tabla de doctores en un objeto Doctor
    private Doctor cursorToDoctor(Cursor cursor) {
        int nameIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_NAME);
        int emailIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_EMAIL);
        int specialityIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_SPECIALITY);
        int passwordIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_PASSWORD);
        int addressIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_ADDRESS);
        int phoneNumberIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_PHONE_NUMBER);
        int birthdayIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_BIRTHDAY);
        int sexIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_SEX);

        String name = cursor.getString(nameIndex);
        String email = cursor.getString(emailIndex);
        String speciality = cursor.getString(specialityIndex);
        String password = cursor.getString(passwordIndex);
        String address = cursor.getString(addressIndex);
        String phoneNumber = cursor.getString(phoneNumberIndex);
        String birthday = cursor.getString(birthdayIndex);
        String sex = cursor.getString(sexIndex);

        Doctor doctor = new Doctor(name, email, speciality, password, address, phoneNumber, birthday, sex);

        return doctor;
    }

    // Convertir el cursor de la tabla de pacientes en un objeto Patient
    private Patient cursorToPatient(Cursor cursor) {
        int nameIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_NAME);
        int emailIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_EMAIL);
        int birthdayIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_BIRTHDAY);
        int weightIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_WEIGHT);
        int heightIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_HEIGHT);
        int bloodIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_BLOOD);
        int passwordIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_PASSWORD);
        int addressIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_ADDRESS);
        int phoneNumberIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_PHONE_NUMBER);
        int sexIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_SEX);

        String name = cursor.getString(nameIndex);
        String email = cursor.getString(emailIndex);
        String address = cursor.getString(addressIndex);
        String phoneNumber = cursor.getString(phoneNumberIndex);
        String birthday = cursor.getString(birthdayIndex);
        String weight = cursor.getString(weightIndex);
        String height = cursor.getString(heightIndex);
        String blood = cursor.getString(bloodIndex);
        String password = cursor.getString(passwordIndex);
        String sex = cursor.getString(sexIndex);

        Patient patient = new Patient(name, email, address, phoneNumber, password, birthday, sex, weight, height, blood);
        return patient;
    }

    public Doctor getDoctorByEmailAndPassword(String email, String password) {
        Doctor doctor = null;
        Cursor cursor = database.query(AppointmentsDatabaseHelper.TABLE_DOCTORS,
                null, AppointmentsDatabaseHelper.COLUMN_EMAIL + " = ? AND " +
                        AppointmentsDatabaseHelper.COLUMN_PASSWORD + " = ?", new String[]{email, password},
                null, null, null);

        if (cursor.moveToFirst()) {
            doctor = cursorToDoctor(cursor);
        }

        cursor.close();
        return doctor;
    }

    public Patient getPatientByEmailAndPassword(String email, String password) {
        Patient patient = null;
        Cursor cursor = database.query(AppointmentsDatabaseHelper.TABLE_PATIENTS,
                null, AppointmentsDatabaseHelper.COLUMN_EMAIL + " = ? AND " +
                        AppointmentsDatabaseHelper.COLUMN_PASSWORD + " = ?", new String[]{email, password},
                null, null, null);

        if (cursor.moveToFirst()) {
            patient = cursorToPatient(cursor);
        }

        cursor.close();
        return patient;
    }
}
