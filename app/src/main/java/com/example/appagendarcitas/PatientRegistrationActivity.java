package com.example.appagendarcitas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.model.Patient;

public class PatientRegistrationActivity extends AppCompatActivity {

    private EditText etPatientName;
    private EditText etPatientEmail;
    private EditText etPatientAddress;
    private EditText etPatientPhone;
    private EditText etPatientBirthday; // Nuevo campo para fecha de cumpleaños
    private EditText etPatientWeight;
    private EditText etPatientHeight;
    private EditText etPatientBlood;
    private EditText etPatientPassword;
    private EditText etPatientSex; // Nuevo campo para sexo

    private AppointmentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        etPatientName = findViewById(R.id.etPatientName);
        etPatientEmail = findViewById(R.id.etPatientEmail);
        etPatientAddress = findViewById(R.id.etPatientAddress);
        etPatientPhone = findViewById(R.id.etPatientPhoneNumber);
        etPatientBirthday = findViewById(R.id.etPatientBirthday);
        etPatientWeight = findViewById(R.id.etPatientWeight);
        etPatientHeight = findViewById(R.id.etPatientHeight);
        etPatientBlood = findViewById(R.id.etPatientBlood);
        etPatientPassword = findViewById(R.id.etPatientPassword);
        etPatientSex = findViewById(R.id.etPatientSex);

        dataSource = new AppointmentsDataSource(this);
    }

    public void createPatient(View view) {
        String name = etPatientName.getText().toString();
        String email = etPatientEmail.getText().toString();
        String address = etPatientAddress.getText().toString();
        String phoneNumber = etPatientPhone.getText().toString();
        String birthday = etPatientBirthday.getText().toString(); // Nuevo campo para fecha de cumpleaños
        double weight = Double.parseDouble(etPatientWeight.getText().toString());
        double height = Double.parseDouble(etPatientHeight.getText().toString());
        String blood = etPatientBlood.getText().toString();
        String password = etPatientPassword.getText().toString();
        String sex = etPatientSex.getText().toString(); // Nuevo campo para sexo

        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() ||
                birthday.isEmpty() || blood.isEmpty() || password.isEmpty() || sex.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_LONG).show();
        } else {
            Patient newPatient = new Patient(name, email, address, phoneNumber, password, birthday, sex, weight, height, blood);

            dataSource.open();
            long insertId = dataSource.insertPatient(newPatient);
            dataSource.close();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Patient Registration");
            if (insertId != -1) {
                builder.setMessage("Patient registered successfully with ID: " + insertId);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PatientRegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                builder.setMessage("Error registering patient.");
                builder.setPositiveButton("OK", null);
            }
            builder.show();
        }
    }
}
