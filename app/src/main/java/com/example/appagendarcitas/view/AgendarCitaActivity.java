package com.example.appagendarcitas.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.R;
import com.example.appagendarcitas.data.AppointmentsDataSource;
import com.example.appagendarcitas.model.AvailableAppointment;
import com.example.appagendarcitas.model.Doctor;

import java.util.List;

public class AgendarCitaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        // Obtén la lista de citas disponibles desde la base de datos
        AppointmentsDataSource dataSource = new AppointmentsDataSource(this);
        dataSource.open();
        List<AvailableAppointment> availableAppointments = dataSource.getAllAvailableAppointments();
        dataSource.close();

        // Configura el ListView para mostrar la lista de citas disponibles usando el ArrayAdapter personalizado
        ListView listView = findViewById(R.id.listViewAvailableAppointments);
        AppointmentListAdapter adapter = new AppointmentListAdapter(this, availableAppointments);
        listView.setAdapter(adapter);
    }

    private class AppointmentListAdapter extends ArrayAdapter<AvailableAppointment> {
        private final LayoutInflater inflater;
        private final AppointmentsDataSource dataSource;

        public AppointmentListAdapter(Context context, List<AvailableAppointment> appointments) {
            super(context, 0, appointments);
            inflater = LayoutInflater.from(context);
            dataSource = new AppointmentsDataSource(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_appointment, parent, false);
            }

            AvailableAppointment appointment = getItem(position);

            TextView textViewDoctor = convertView.findViewById(R.id.textViewDoctor);
            TextView textViewSpeciality = convertView.findViewById(R.id.textViewSpeciality);
            TextView textViewDate = convertView.findViewById(R.id.textViewDate);
            TextView textViewTime = convertView.findViewById(R.id.textViewTime);

            // Consulta la base de datos para obtener el nombre del doctor y su especialidad
            dataSource.open();
            Doctor doctor = dataSource.getDoctorById((int) appointment.getDoctorId());
            dataSource.close();

            if (doctor != null) {
                textViewDoctor.setText("Nombre del doctor: " + doctor.getName());
                textViewSpeciality.setText("Especialidad: " + doctor.getSpeciality());
            }

            textViewDate.setText("Fecha: " + appointment.getDate());
            textViewTime.setText("Hora: " + appointment.getTime());

            return convertView;
        }
    }
}
