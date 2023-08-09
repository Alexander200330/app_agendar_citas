package com.example.appagendarcitas.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.MainActivity;
import com.example.appagendarcitas.R;
import com.example.appagendarcitas.data.AppointmentsDataSource;
import com.example.appagendarcitas.model.Doctor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DoctorRegistrationActivity extends AppCompatActivity {

    private EditText etDoctorName;
    private EditText etDoctorEmail;
    private EditText etDoctorSpeciality;
    private EditText etDoctorPassword;
    private EditText etDoctorAddress;
    private EditText etDoctorPhoneNumber;
    private EditText etDoctorBirthday;
    private Spinner spDoctorSex;

    private AppointmentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        etDoctorName = findViewById(R.id.etDoctorName);
        etDoctorEmail = findViewById(R.id.etDoctorEmail);
        etDoctorSpeciality = findViewById(R.id.etDoctorSpeciality);
        etDoctorPassword = findViewById(R.id.etDoctorPassword);
        etDoctorAddress = findViewById(R.id.etDoctorAddress);
        etDoctorPhoneNumber = findViewById(R.id.etDoctorPhoneNumber);
        etDoctorBirthday = findViewById(R.id.etDoctorBirthday);
        spDoctorSex = findViewById(R.id.spDoctorSex);

        dataSource = new AppointmentsDataSource(this);

        etDoctorBirthday.setOnClickListener(new View.OnClickListener() {
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
                etDoctorBirthday.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    public void createDoctor(View view) {
        String name = etDoctorName.getText().toString();
        String email = etDoctorEmail.getText().toString();
        String speciality = etDoctorSpeciality.getText().toString();
        String password = etDoctorPassword.getText().toString();
        String address = etDoctorAddress.getText().toString();
        String phoneNumber = etDoctorPhoneNumber.getText().toString();
        String birthday = etDoctorBirthday.getText().toString();
        String sex = spDoctorSex.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty() || speciality.isEmpty() || password.isEmpty() ||
                address.isEmpty() || phoneNumber.isEmpty() || birthday.isEmpty() || sex.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_LONG).show();
        } else {
            Doctor newDoctor = new Doctor(name, email, speciality, password, address, phoneNumber, birthday, sex);

            dataSource.open();
            long insertId = dataSource.insertDoctor(newDoctor);
            dataSource.close();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Registro de Doctor");
            if (insertId != -1) {
                builder.setMessage("Doctor registrado con éxito con ID: " + insertId);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DoctorRegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                builder.setMessage("Error al registrar el doctor.");
                builder.setPositiveButton("OK", null);
            }
            builder.show();
        }
    }
}
