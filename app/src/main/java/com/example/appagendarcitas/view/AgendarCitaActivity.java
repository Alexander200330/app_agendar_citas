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

        public AppointmentListAdapter(Context context, List<AvailableAppointment> appointments) {
            super(context, 0, appointments);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            AvailableAppointment appointment = getItem(position);

            TextView textView1 = convertView.findViewById(android.R.id.text1);
            TextView textView2 = convertView.findViewById(android.R.id.text2);

            // Personaliza cómo se muestra la información en los TextViews
            textView1.setText("Fecha: " + appointment.getDate());
            textView2.setText("Hora: " + appointment.getTime());

            return convertView;
        }
    }
}
