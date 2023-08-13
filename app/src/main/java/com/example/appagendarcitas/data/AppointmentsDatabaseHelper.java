package com.example.appagendarcitas.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppointmentsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "appointments.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_DOCTORS = "doctors";
    public static final String TABLE_PATIENTS = "patients";
    public static final String TABLE_APPOINTMENTS = "appointments"; // Nueva tabla para citas disponibles

    // Common column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password"; // Nuevo campo para la contraseña

    // Doctor table specific column names
    public static final String COLUMN_SPECIALITY = "speciality";
    public static final String COLUMN_ADDRESS = "address"; // Nuevo campo para la dirección
    public static final String COLUMN_PHONE_NUMBER = "phone_number"; // Nuevo campo para el número de teléfono
    public static final String COLUMN_BIRTHDAY = "birthday"; // Nuevo campo para la fecha de cumpleaños
    public static final String COLUMN_SEX = "sex"; // Nuevo campo para el sexo

    // Patient table specific column names
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_BLOOD = "blood";

    // Appointments table specific column names
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DOCTOR_ID = "doctor_id";

    // Create table statements
    private static final String CREATE_TABLE_DOCTORS =
            "CREATE TABLE " + TABLE_DOCTORS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_SPECIALITY + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_PHONE_NUMBER + " TEXT, " +
                    COLUMN_BIRTHDAY + " TEXT, " +
                    COLUMN_SEX + " TEXT" +
                    ");";
    private static final String CREATE_TABLE_PATIENTS =
            "CREATE TABLE " + TABLE_PATIENTS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_BIRTHDAY + " TEXT, " +
                    COLUMN_WEIGHT + " TEXT, " +
                    COLUMN_HEIGHT + " TEXT, " +
                    COLUMN_BLOOD + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_PHONE_NUMBER + " TEXT, " +
                    COLUMN_SEX + " TEXT" +
                    ");";
    private static final String CREATE_TABLE_APPOINTMENTS =
            "CREATE TABLE " + TABLE_APPOINTMENTS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_TIME + " TEXT, " +
                    COLUMN_DOCTOR_ID + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_DOCTOR_ID + ") REFERENCES " + TABLE_DOCTORS + "(" + COLUMN_ID + ")" +
                    ");";

    public AppointmentsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DOCTORS);
        db.execSQL(CREATE_TABLE_PATIENTS);
        db.execSQL(CREATE_TABLE_APPOINTMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }
}
