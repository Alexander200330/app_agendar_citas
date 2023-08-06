package com.example.appagendarcitas;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PatientLoginActivity extends AppCompatActivity {

    private EditText etPasswordPatient;
    private CheckBox chkShowPasswordPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);

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

    // Resto de la lógica para el inicio de sesión del paciente, si es necesario
}
