package com.example.appagendarcitas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.model.Patient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    private Spinner spPatientSex;

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
        spPatientSex = findViewById(R.id.spPatientSex); // Cambiado a Spinner

        dataSource = new AppointmentsDataSource(this);

        etPatientBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String selectedDate = sdf.format(calendar.getTime());
                etPatientBirthday.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    public void createPatient(View view) {
        String name = etPatientName.getText().toString();
        String email = etPatientEmail.getText().toString();
        String address = etPatientAddress.getText().toString();
        String phoneNumber = etPatientPhone.getText().toString();
        String birthday = etPatientBirthday.getText().toString(); // Nuevo campo para fecha de cumpleaños
        String weightStr = etPatientWeight.getText().toString();
        String heightStr = etPatientHeight.getText().toString();
        String blood = etPatientBlood.getText().toString();
        String password = etPatientPassword.getText().toString();
        String sex = spPatientSex.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() ||
                birthday.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty() ||
                blood.isEmpty() || password.isEmpty() || sex.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_LONG).show();
        } else {
            Patient newPatient = new Patient(name, email, address, phoneNumber, password, birthday, sex, weightStr, heightStr, blood);

            dataSource.open();
            long insertId = dataSource.insertPatient(newPatient);
            dataSource.close();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Registro de Paciente");
            if (insertId != -1) {
                builder.setMessage("Paciente registrado exitosamente con ID: " + insertId);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PatientRegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                builder.setMessage("Error al registrar el paciente.");
                builder.setPositiveButton("OK", null);
            }
            builder.show();
        }
    }
}
