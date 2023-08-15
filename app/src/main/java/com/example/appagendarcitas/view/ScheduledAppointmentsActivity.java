package com.example.appagendarcitas.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.R;

import java.util.ArrayList;

public class ScheduledAppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_appointments);

        // Obtén los detalles de las citas agendadas del Intent
        ArrayList<String> appointmentDetails = getIntent().getStringArrayListExtra("appointmentDetails");

        // Encuentra el TextView en el layout para mostrar los detalles
        TextView tvAppointmentDetails = findViewById(R.id.tvAppointmentDetails);

        // Concatena y muestra los detalles de las citas agendadas en el TextView
        StringBuilder allAppointmentsText = new StringBuilder();
        for (String details : appointmentDetails) {
            allAppointmentsText.append(details);
        }
        tvAppointmentDetails.setText(allAppointmentsText.toString());
    }
}
