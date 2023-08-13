package com.example.appagendarcitas.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.MainActivity;
import com.example.appagendarcitas.R;
import com.example.appagendarcitas.data.AppointmentsDataSource;
import com.example.appagendarcitas.model.AvailableAppointment;
import com.example.appagendarcitas.model.Doctor;

import java.util.Calendar;

public class DoctorMenuActivity extends AppCompatActivity {

    private TextView tvProfileDoctor;
    private EditText etSelectedDate;
    private EditText etSelectedTime;
    private AppointmentsDataSource dataSource;

    private View dialogView;

    int doctorId;

    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_menu);

        tvProfileDoctor = findViewById(R.id.tvProfileDoctor);

        doctorId = getIntent().getIntExtra("doctorId", -1);

        Log.d("DoctorMenuActivity", "Doctor ID: " + doctorId);



        // Obtén el nombre del doctor del Intent
        String doctorName = getIntent().getStringExtra("doctorName");
        if (doctorName != null) {
            tvProfileDoctor.setText("Perfil de " + doctorName);
        }

        dataSource = new AppointmentsDataSource(this);
        dataSource.open();
    }

    public void openAddAvailableAppointmentModal(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialogView = LayoutInflater.from(this).inflate(R.layout.modal_add_available_appointment, null);
        etSelectedDate = dialogView.findViewById(R.id.etSelectedDate);
        etSelectedTime = dialogView.findViewById(R.id.etSelectedTime);

        // Configura la funcionalidad de abrir los Date/Time Pickers al hacer clic en los EditText correspondientes
        etSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        etSelectedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        builder.setView(dialogView);
        builder.setTitle("Agregar Cita Disponible");
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String date = etSelectedDate.getText().toString();
            String time = etSelectedTime.getText().toString();

            // Agrega un log para imprimir el valor del doctorId

            AvailableAppointment availableAppointment = new AvailableAppointment(date, time, doctorId);
            long appointmentId = dataSource.insertAvailableAppointment(availableAppointment);

            if (appointmentId != -1) {
                Toast.makeText(this, "Cita disponible guardada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar la cita disponible", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDatePickerDialog() {
        // Obtén la fecha actual para mostrar en el DatePicker
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Crea un DatePickerDialog con la fecha actual
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedYear = year;
                        selectedMonth = monthOfYear + 1; // Los meses en DatePicker son 0-indexed
                        selectedDay = dayOfMonth;

                        // Actualiza el EditText con la fecha seleccionada
                        etSelectedDate.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                    }
                },
                currentYear, currentMonth, currentDay);

        // Muestra el DatePickerDialog
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        // Obtén la hora actual para mostrar en el TimePicker
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Crea un TimePickerDialog con la hora actual
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;

                        // Actualiza el EditText con la hora seleccionada
                        etSelectedTime.setText(selectedHour + ":" + selectedMinute);
                    }
                },
                currentHour, currentMinute, true);

        // Muestra el TimePickerDialog
        timePickerDialog.show();
    }
}
