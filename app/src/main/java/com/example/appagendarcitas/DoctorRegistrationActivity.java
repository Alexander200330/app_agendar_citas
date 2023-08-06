package com.example.appagendarcitas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.model.Doctor;

public class DoctorRegistrationActivity extends AppCompatActivity {

    private EditText etDoctorName;
    private EditText etDoctorEmail;
    private EditText etDoctorSpeciality;
    private EditText etDoctorPassword;
    private EditText etDoctorAddress;
    private EditText etDoctorPhoneNumber;

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

        dataSource = new AppointmentsDataSource(this);
        dataSource.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }

    public void registerDoctor(View view) {
        String name = etDoctorName.getText().toString().trim();
        String email = etDoctorEmail.getText().toString().trim();
        String address = etDoctorAddress.getText().toString().trim();
        String phoneNumber = etDoctorPhoneNumber.getText().toString().trim();
        String password = etDoctorPassword.getText().toString(); // Obtener el password sin hash
        String speciality = etDoctorSpeciality.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || speciality.isEmpty()) {
            showToast("Todos los campos son obligatorios.");
        } else {
            Doctor doctor = new Doctor(name, email, address, phoneNumber, password, speciality); // Pasar el password sin hash
            try {
                long id = dataSource.insertDoctor(doctor);
                if (id != -1) {
                    showToast("Registro Exitoso. ¡El registro se ha completado con éxito!");
                    navigateToLoginActivity();
                } else {
                    showToast("Error. No se pudo completar el registro. Intente nuevamente.");
                }
            } catch (Exception e) {
                showToast("Error. " + e.getMessage() + ". Intente nuevamente.");
                e.printStackTrace();
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}
