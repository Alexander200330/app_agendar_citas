package com.example.appagendarcitas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.view.DoctorLoginActivity;
import com.example.appagendarcitas.view.PatientLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Método para iniciar sesión como Doctor
    public void loginDoctor(View view) {
        Intent intent = new Intent(this, DoctorLoginActivity.class);
        startActivity(intent);
    }

    // Método para iniciar sesión como Paciente
    public void loginPaciente(View view) {
        Intent intent = new Intent(this, PatientLoginActivity.class);
        startActivity(intent);
    }

    // Método para volver al menú principal
    public void goBackToMenu(View view) {
        finish();
    }
}
