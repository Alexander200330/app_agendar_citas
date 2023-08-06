package com.example.appagendarcitas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.model.Patient;

public class PatientRegistrationActivity extends AppCompatActivity {

    private EditText etPatientName;
    private EditText etPatientEmail;
    private EditText etPatientAddress;
    private EditText etPatientPhone;
    private EditText etPatientBirthday;
    private EditText etPatientWeight;
    private EditText etPatientHeight;
    private EditText etPatientBlood;
    private EditText etPatientPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        etPatientName = findViewById(R.id.et_patient_name);
        etPatientEmail = findViewById(R.id.et_patient_email);
        etPatientAddress = findViewById(R.id.et_patient_address);
        etPatientPhone = findViewById(R.id.et_patient_phone);
        etPatientBirthday = findViewById(R.id.et_patient_birthday);
        etPatientWeight = findViewById(R.id.et_patient_weight);
        etPatientHeight = findViewById(R.id.et_patient_height);
        etPatientBlood = findViewById(R.id.et_patient_blood);
        etPatientPassword = findViewById(R.id.et_patient_password);
    }

    public void registerPatient(View view) {
        String name = etPatientName.getText().toString();
        String email = etPatientEmail.getText().toString();
        String address = etPatientAddress.getText().toString();
        String phone = etPatientPhone.getText().toString();
        String birthday = etPatientBirthday.getText().toString();
        double weight = Double.parseDouble(etPatientWeight.getText().toString());
        double height = Double.parseDouble(etPatientHeight.getText().toString());
        String blood = etPatientBlood.getText().toString();
        String password = etPatientPassword.getText().toString();

        Patient patient = new Patient(name, email, address, phone, password, birthday, weight, height, blood);
        // Guardar el paciente en la base de datos
        AppointmentsDataSource dataSource = new AppointmentsDataSource(this);
        dataSource.open();
        dataSource.insertPatient(patient);
        dataSource.close();

        // Llevar al usuario a otra ventana con el mensaje de bienvenida
        String welcomeMessage = "Bienvenido " + name;
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("message", welcomeMessage);
        startActivity(intent);
    }
}
