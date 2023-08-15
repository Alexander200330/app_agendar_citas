package com.example.appagendarcitas.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.R;
import com.example.appagendarcitas.data.AppointmentsDataSource;
import com.example.appagendarcitas.model.AvailableAppointment;
import com.example.appagendarcitas.model.Doctor;
import com.example.appagendarcitas.model.ScheduledAppointment;

import java.util.List;

public class AgendarCitaActivity extends AppCompatActivity {

    private AppointmentsDataSource dataSource;
    int idPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        dataSource = new AppointmentsDataSource(this);

        dataSource.open();
        List<AvailableAppointment> availableAppointments = dataSource.getAllAvailableAppointments();
        dataSource.close();

        ListView listView = findViewById(R.id.listViewAvailableAppointments);
        AppointmentListAdapter adapter = new AppointmentListAdapter(this, availableAppointments);
        listView.setAdapter(adapter);
    }

    private class AppointmentListAdapter extends ArrayAdapter<AvailableAppointment> {
        private final LayoutInflater inflater;

        public AppointmentListAdapter(Context context, List<AvailableAppointment> appointments) {
            super(context, 0, appointments);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_appointment, parent, false);
            }

            final AvailableAppointment appointment = getItem(position);

            TextView textViewDoctor = convertView.findViewById(R.id.textViewDoctor);
            TextView textViewSpeciality = convertView.findViewById(R.id.textViewSpeciality);
            TextView textViewDate = convertView.findViewById(R.id.textViewDate);
            TextView textViewTime = convertView.findViewById(R.id.textViewTime);

            dataSource.open();
            Doctor doctor = dataSource.getDoctorById((int) appointment.getDoctorId());
            dataSource.close();

            if (doctor != null) {
                textViewDoctor.setText(doctor.getName());
                textViewSpeciality.setText(doctor.getSpeciality());
            }

            textViewDate.setText(appointment.getDate());
            textViewTime.setText(appointment.getTime());

            ImageButton addButton = convertView.findViewById(R.id.boton_agregar);

            // obtener el id del paciente desde el intent

            idPatient = getIntent().getIntExtra("patientId", -1);

            ScheduledAppointment scheduledAppointment = new ScheduledAppointment(
                    appointment.getDate(),
                    appointment.getTime(),
                    (int) appointment.getDoctorId(),
                    idPatient
            );

            // Obtengo el id de la cita disponible
            int id = (int) dataSource.getAvailableAppointmentId(appointment.getDate(), appointment.getTime(), (int) appointment.getDoctorId());

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scheduleAppointment(scheduledAppointment, id);
                }
            });

            return convertView;
        }
    }

    private void scheduleAppointment(ScheduledAppointment scheduledAppointment, int id) {

            dataSource.open();
            long scheduledId = dataSource.insertScheduledAppointment(scheduledAppointment);
            dataSource.deleteAvailableAppointment(id);
            dataSource.close();

            if (scheduledId != -1) {
                refreshAppointmentList();
                Toast.makeText(this, "Cita agendada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se pudo agendar la cita", Toast.LENGTH_SHORT).show();
            }
    }

    private void refreshAppointmentList() {
        dataSource.open();
        List<AvailableAppointment> availableAppointments = dataSource.getAllAvailableAppointments();
        dataSource.close();

        ListView listView = findViewById(R.id.listViewAvailableAppointments);
        AppointmentListAdapter adapter = new AppointmentListAdapter(this, availableAppointments);
        listView.setAdapter(adapter);
    }


    private int getPatientIdByEmailAndPassword(String email, String password) {
        // Implementa la lógica para obtener el id del paciente actual
        // Puede ser a través de autenticación o selección
        return 1; // Ejemplo: retorno de un valor fijo para la demostración
    }
}
