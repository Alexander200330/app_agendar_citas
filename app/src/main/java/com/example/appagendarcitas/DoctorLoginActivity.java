package com.example.appagendarcitas;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText etPasswordDoctor;
    private CheckBox chkShowPasswordDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);

        etPasswordDoctor = findViewById(R.id.etPasswordDoctor);
        chkShowPasswordDoctor = findViewById(R.id.chkShowPasswordDoctor);
    }

    public void showHidePasswordDoctor(View view) {
        if (chkShowPasswordDoctor.isChecked()) {
            etPasswordDoctor.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            etPasswordDoctor.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    // Resto de la lógica para el inicio de sesión del doctor, si es necesario
}
