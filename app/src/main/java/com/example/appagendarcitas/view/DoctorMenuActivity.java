package com.example.appagendarcitas.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.MainActivity;
import com.example.appagendarcitas.R;

public class DoctorMenuActivity extends AppCompatActivity {

    private TextView tvProfileDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_menu);

        tvProfileDoctor = findViewById(R.id.tvProfileDoctor);

        // Obtén el nombre del doctor del Intent
        String doctorName = getIntent().getStringExtra("doctorName");
        if (doctorName != null) {
            tvProfileDoctor.setText("Perfil de " + doctorName);
        }
    }

    public void addAvailableAppointment(View view) {
        // Agregar código para abrir la actividad de agregar citas disponibles
    }

    public void viewSchedule(View view) {
        // Agregar código para abrir la actividad de ver horario
    }

    public void logout(View view) {
        // Agregar código para cerrar sesión y redirigir a la pantalla de inicio de sesión
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Cierra esta actividad
    }
}
