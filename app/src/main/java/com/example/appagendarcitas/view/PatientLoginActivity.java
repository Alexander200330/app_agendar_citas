package com.example.appagendarcitas.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.R;
import com.example.appagendarcitas.data.AppointmentsDataSource;
import com.example.appagendarcitas.model.Patient;

public class PatientLoginActivity extends AppCompatActivity {

    private EditText etEmailPatient;
    private EditText etPasswordPatient;
    private CheckBox chkShowPasswordPatient;
    private AppointmentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);

        dataSource = new AppointmentsDataSource(this);
        dataSource.open();

        etEmailPatient = findViewById(R.id.etEmailPatient);
        etPasswordPatient = findViewById(R.id.etPasswordPatient);
        chkShowPasswordPatient = findViewById(R.id.chkShowPasswordPatient);
    }

    public void showHidePasswordPatient(View view) {
        if (chkShowPasswordPatient.isChecked()) {
            etPasswordPatient.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            etPasswordPatient.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void loginPatient(View view) {
        String email = etEmailPatient.getText().toString().trim();
        String password = etPasswordPatient.getText().toString().trim();

        Patient patient = dataSource.getPatientByEmailAndPassword(email, password);

        if (patient != null) {
            showAlertDialog("Inicio de sesión exitoso", "Bienvenido " + patient.getName());
        } else {
            showAlertDialog("Error", "Credenciales inválidas. Verifique su correo y contraseña.");
        }
    }

    public void goToPatientRegister(View view) {
        Intent intent = new Intent(this, PatientRegistrationActivity.class);
        startActivity(intent);
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
