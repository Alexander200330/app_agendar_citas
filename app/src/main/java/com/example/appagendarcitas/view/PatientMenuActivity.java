package com.example.appagendarcitas.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.MainActivity;
import com.example.appagendarcitas.R;
import com.example.appagendarcitas.data.AppointmentsDataSource;
import com.example.appagendarcitas.model.AvailableAppointment;
import com.example.appagendarcitas.model.Doctor;
import com.example.appagendarcitas.model.ScheduledAppointment;

import java.io.Serializable;
import java.util.List;

public class PatientMenuActivity extends AppCompatActivity {

    AppointmentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_menu);

        dataSource = new AppointmentsDataSource(this);

        // Obtén el nombre del paciente del Intent
        String patientName = getIntent().getStringExtra("patientName");
        if (patientName != null) {
            TextView tvProfilePatient = findViewById(R.id.tvProfilePatient);
            tvProfilePatient.setText("Perfil de " + patientName);
        }


    }

    public void agendarCita(View view) {
        String patientEmail = getIntent().getStringExtra("patientEmail");
        String patientPassword = getIntent().getStringExtra("patientPassword");

        dataSource.open();
        int patientId = dataSource.getPatientIdByEmailAndPassword(patientEmail, patientPassword);
        dataSource.close();

        // Crea un Intent para abrir la pantalla de AgendarCitaActivity
        Intent intent = new Intent(this, AgendarCitaActivity.class);
        intent.putExtra("patientId", patientId);
        startActivity(intent);
    }


    public void verCitasAgendadas(View view) {
        String patientEmail = getIntent().getStringExtra("patientEmail");
        String patientPassword = getIntent().getStringExtra("patientPassword");

        dataSource.open();
        int patientId = dataSource.getPatientIdByEmailAndPassword(patientEmail, patientPassword);

        // Obtener la lista de citas agendadas para el paciente
        List<ScheduledAppointment> scheduledAppointments = dataSource.getScheduledAppointmentsForPatient(patientId);

        StringBuilder appointmentsText = new StringBuilder();
        for (ScheduledAppointment appointment : scheduledAppointments) {
            Doctor doctor = dataSource.getDoctorById(appointment.getDoctorId());

            appointmentsText.append("Doctor: ").append(doctor.getName())
                    .append("\nEspecialidad: ").append(doctor.getSpeciality())
                    .append("\nFecha: ").append(appointment.getDate())
                    .append("\nHora: ").append(appointment.getTime())

                    .append("\n\n");
        }

        // Mostrar las citas agendadas con los detalles del doctor en un AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Citas Agendadas")
                .setMessage(appointmentsText.toString())
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
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
