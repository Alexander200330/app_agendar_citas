package com.example.appagendarcitas.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.appagendarcitas.data.AppointmentsDatabaseHelper;
import com.example.appagendarcitas.model.AvailableAppointment;
import com.example.appagendarcitas.model.Doctor;
import com.example.appagendarcitas.model.Patient;
import com.example.appagendarcitas.model.ScheduledAppointment;

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
        int idIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_NAME);
        int emailIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_EMAIL);
        int specialityIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_SPECIALITY);
        int passwordIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_PASSWORD);
        int addressIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_ADDRESS);
        int phoneNumberIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_PHONE_NUMBER);
        int birthdayIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_BIRTHDAY);
        int sexIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_SEX);

        int id = cursor.getInt(idIndex);
        String name = cursor.getString(nameIndex);
        String email = cursor.getString(emailIndex);
        String speciality = cursor.getString(specialityIndex);
        String password = cursor.getString(passwordIndex);
        String address = cursor.getString(addressIndex);
        String phoneNumber = cursor.getString(phoneNumberIndex);
        String birthday = cursor.getString(birthdayIndex);
        String sex = cursor.getString(sexIndex);

        return new Doctor(id, name, email, speciality, password, address, phoneNumber, birthday, sex);
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

    public long insertAvailableAppointment(AvailableAppointment availableAppointment) {
        ContentValues values = new ContentValues();
        values.put(AppointmentsDatabaseHelper.COLUMN_DATE, availableAppointment.getDate());
        values.put(AppointmentsDatabaseHelper.COLUMN_TIME, availableAppointment.getTime());
        values.put(AppointmentsDatabaseHelper.COLUMN_DOCTOR_ID, availableAppointment.getDoctorId());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(AppointmentsDatabaseHelper.TABLE_APPOINTMENTS, null, values);
    }

    // Dentro de la clase AppointmentsDataSource

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

    @SuppressLint("Range")
    public int getDoctorIdByEmailAndPassword(String email, String password) {
        int doctorId = -1; // Valor por defecto en caso de que no se encuentre el doctor

        String[] projection = {AppointmentsDatabaseHelper.COLUMN_ID};
        String selection = AppointmentsDatabaseHelper.COLUMN_EMAIL + " = ? AND " +
                AppointmentsDatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = database.query(
                AppointmentsDatabaseHelper.TABLE_DOCTORS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            doctorId = cursor.getInt(cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_ID));
        }

        cursor.close();

        return doctorId;
    }

    @SuppressLint("Range")
    public int getPatientIdByEmailAndPassword(String email, String password) {
        int patientId = -1;

        String[] projection = {AppointmentsDatabaseHelper.COLUMN_ID};
        String selection = AppointmentsDatabaseHelper.COLUMN_EMAIL + " = ? AND " +
                AppointmentsDatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = database.query(
                AppointmentsDatabaseHelper.TABLE_PATIENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            patientId = cursor.getInt(cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_ID));
        }

        cursor.close();

        return patientId;
    }

    // Agregué este método para convertir el cursor en un objeto AvailableAppointment
    public List<AvailableAppointment> getAllAvailableAppointments() {
        List<AvailableAppointment> availableAppointments = new ArrayList<>();
        Cursor cursor = database.query(AppointmentsDatabaseHelper.TABLE_APPOINTMENTS,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AvailableAppointment availableAppointment = cursorToAvailableAppointment(cursor);
            availableAppointments.add(availableAppointment);
            cursor.moveToNext();
        }

        cursor.close();
        return availableAppointments;
    }

    private AvailableAppointment cursorToAvailableAppointment(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_ID);
        int dateIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_DATE);
        int timeIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_TIME);
        int doctorIdIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_DOCTOR_ID);

        int id = cursor.getInt(idIndex);
        String date = cursor.getString(dateIndex);
        String time = cursor.getString(timeIndex);
        int doctorId = cursor.getInt(doctorIdIndex);

        return new AvailableAppointment(date, time, doctorId);
    }

    public Doctor getDoctorById(int doctorId) {
        Doctor doctor = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                AppointmentsDatabaseHelper.COLUMN_NAME,
                AppointmentsDatabaseHelper.COLUMN_EMAIL,
                AppointmentsDatabaseHelper.COLUMN_SPECIALITY,
                AppointmentsDatabaseHelper.COLUMN_PASSWORD,
                AppointmentsDatabaseHelper.COLUMN_ADDRESS,
                AppointmentsDatabaseHelper.COLUMN_PHONE_NUMBER,
                AppointmentsDatabaseHelper.COLUMN_BIRTHDAY,
                AppointmentsDatabaseHelper.COLUMN_SEX
        };
        String selection = AppointmentsDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(doctorId)};

        try (Cursor cursor = db.query(
                AppointmentsDatabaseHelper.TABLE_DOCTORS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_EMAIL));
                String speciality = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_SPECIALITY));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_PASSWORD));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_ADDRESS));
                String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_PHONE_NUMBER));
                String birthday = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_BIRTHDAY));
                String sex = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_SEX));

                doctor = new Doctor(doctorId, name, email, speciality, password, address, phoneNumber, birthday, sex);
            }
        }

        return doctor;
    }

    public long insertScheduledAppointment(ScheduledAppointment scheduledAppointment) {
        ContentValues values = new ContentValues();
        values.put(AppointmentsDatabaseHelper.COLUMN_DATE, scheduledAppointment.getDate());
        values.put(AppointmentsDatabaseHelper.COLUMN_TIME, scheduledAppointment.getTime());
        values.put(AppointmentsDatabaseHelper.COLUMN_DOCTOR_ID, scheduledAppointment.getDoctorId());
        values.put(AppointmentsDatabaseHelper.COLUMN_PATIENT_ID, scheduledAppointment.getPatientId());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long insertedRowId = db.insert(AppointmentsDatabaseHelper.TABLE_SCHEDULED_APPOINTMENTS, null, values);

        if (insertedRowId != -1) {
            // If insertion was successful, delete the appointment from the available appointments
            String whereClause = AppointmentsDatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(scheduledAppointment.getAvailableAppointmentId())};
            db.delete(AppointmentsDatabaseHelper.TABLE_APPOINTMENTS, whereClause, whereArgs);
        }

        return insertedRowId;
    }

    // Delete an available appointment by ID
    public void deleteAvailableAppointment(long id) {
        database.delete(AppointmentsDatabaseHelper.TABLE_APPOINTMENTS,
                AppointmentsDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public long getAvailableAppointmentId(String date, String time, int doctorId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {AppointmentsDatabaseHelper.COLUMN_ID};
        String selection = AppointmentsDatabaseHelper.COLUMN_DATE + " = ? AND " +
                AppointmentsDatabaseHelper.COLUMN_TIME + " = ? AND " +
                AppointmentsDatabaseHelper.COLUMN_DOCTOR_ID + " = ?";
        String[] selectionArgs = {date, time, String.valueOf(doctorId)};

        long appointmentId = -1;

        try (Cursor cursor = db.query(
                AppointmentsDatabaseHelper.TABLE_APPOINTMENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                appointmentId = cursor.getLong(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_ID));
            }
        }

        return appointmentId;
    }

    public List<ScheduledAppointment> getScheduledAppointmentsForPatient(int patientId) {
        List<ScheduledAppointment> scheduledAppointments = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                AppointmentsDatabaseHelper.COLUMN_ID,
                AppointmentsDatabaseHelper.COLUMN_DATE,
                AppointmentsDatabaseHelper.COLUMN_TIME,
                AppointmentsDatabaseHelper.COLUMN_DOCTOR_ID,
                AppointmentsDatabaseHelper.COLUMN_PATIENT_ID
        };
        String selection = AppointmentsDatabaseHelper.COLUMN_PATIENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(patientId)};

        try (Cursor cursor = db.query(
                AppointmentsDatabaseHelper.TABLE_SCHEDULED_APPOINTMENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ScheduledAppointment scheduledAppointment = cursorToScheduledAppointment(cursor);
                scheduledAppointments.add(scheduledAppointment);
                cursor.moveToNext();
            }
        }

        return scheduledAppointments;
    }

    private ScheduledAppointment cursorToScheduledAppointment(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_ID);
        int dateIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_DATE);
        int timeIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_TIME);
        int doctorIdIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_DOCTOR_ID);
        int patientIdIndex = cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_PATIENT_ID);

        int id = cursor.getInt(idIndex);
        String date = cursor.getString(dateIndex);
        String time = cursor.getString(timeIndex);
        int doctorId = cursor.getInt(doctorIdIndex);
        int patientId = cursor.getInt(patientIdIndex);

        return new ScheduledAppointment(date, time, doctorId, patientId);
    }

    public List<AvailableAppointment> getAllAvailableAppointmentsForDoctor(String doctorEmail, String doctorPassword) {
        int doctorId = getDoctorIdByEmailAndPassword(doctorEmail, doctorPassword);
        return getAllAvailableAppointmentsForDoctorId(doctorId);
    }

    public List<AvailableAppointment> getAllAvailableAppointmentsForDoctorId(int doctorId) {
        List<AvailableAppointment> availableAppointments = new ArrayList<>();

        String[] projection = {AppointmentsDatabaseHelper.COLUMN_DATE, AppointmentsDatabaseHelper.COLUMN_TIME};
        String selection = AppointmentsDatabaseHelper.COLUMN_DOCTOR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(doctorId)};

        Cursor cursor = database.query(
                AppointmentsDatabaseHelper.TABLE_APPOINTMENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_DATE));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_TIME));
            availableAppointments.add(new AvailableAppointment(date, time, doctorId));
            cursor.moveToNext();
        }

        cursor.close();
        return availableAppointments;
    }

    public List<ScheduledAppointment> getScheduledAppointmentsForDoctor(int doctorId) {
        List<ScheduledAppointment> scheduledAppointments = new ArrayList<>();

        String[] projection = {
                AppointmentsDatabaseHelper.COLUMN_ID,
                AppointmentsDatabaseHelper.COLUMN_DATE,
                AppointmentsDatabaseHelper.COLUMN_TIME,
                AppointmentsDatabaseHelper.COLUMN_DOCTOR_ID,
                AppointmentsDatabaseHelper.COLUMN_PATIENT_ID
        };
        String selection = AppointmentsDatabaseHelper.COLUMN_DOCTOR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(doctorId)};

        Cursor cursor = database.query(
                AppointmentsDatabaseHelper.TABLE_SCHEDULED_APPOINTMENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ScheduledAppointment scheduledAppointment = cursorToScheduledAppointment(cursor);
            scheduledAppointments.add(scheduledAppointment);
            cursor.moveToNext();
        }

        cursor.close();
        return scheduledAppointments;
    }

    public Patient getPatientById(int patientId) {
        Patient patient = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                AppointmentsDatabaseHelper.COLUMN_NAME,
                AppointmentsDatabaseHelper.COLUMN_EMAIL,
                AppointmentsDatabaseHelper.COLUMN_BIRTHDAY,
                AppointmentsDatabaseHelper.COLUMN_WEIGHT,
                AppointmentsDatabaseHelper.COLUMN_HEIGHT,
                AppointmentsDatabaseHelper.COLUMN_BLOOD,
                AppointmentsDatabaseHelper.COLUMN_PASSWORD,
                AppointmentsDatabaseHelper.COLUMN_ADDRESS,
                AppointmentsDatabaseHelper.COLUMN_PHONE_NUMBER,
                AppointmentsDatabaseHelper.COLUMN_SEX
        };
        String selection = AppointmentsDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(patientId)};

        try (Cursor cursor = db.query(
                AppointmentsDatabaseHelper.TABLE_PATIENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_EMAIL));
                String birthday = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_BIRTHDAY));
                String weight = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_WEIGHT));
                String height = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_HEIGHT));
                String blood = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_BLOOD));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_PASSWORD));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_ADDRESS));
                String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_PHONE_NUMBER));
                String sex = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentsDatabaseHelper.COLUMN_SEX));

                patient = new Patient(name, email, address, phoneNumber, password, birthday, sex, weight, height, blood);
            }
        }

        return patient;
    }


}
