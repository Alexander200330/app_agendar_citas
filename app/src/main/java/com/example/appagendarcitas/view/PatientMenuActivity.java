package com.example.appagendarcitas.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.MainActivity;
import com.example.appagendarcitas.R;

public class PatientMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_menu);

        // Obtén el nombre del paciente del Intent
        String patientName = getIntent().getStringExtra("patientName");
        if (patientName != null) {
            TextView tvProfilePatient = findViewById(R.id.tvProfilePatient);
            tvProfilePatient.setText("Perfil de " + patientName);
        }
    }

    public void agendarCita(View view) {
        // Lógica para abrir la pantalla de agendar cita
        // Puedes crear una nueva actividad o fragmento para la funcionalidad de agendar cita
    }

    public void verCitasAgendadas(View view) {
        // Lógica para abrir la pantalla de ver citas agendadas
        // Puedes crear una nueva actividad o fragmento para la funcionalidad de ver citas agendadas
    }

    public void logout(View view) {
        // Lógica para realizar el logout
        // Por ejemplo, regresar a la pantalla de login o cerrar la sesión actual
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
