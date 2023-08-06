package com.example.appagendarcitas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.model.Doctor;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText etEmailDoctor;
    private EditText etPasswordDoctor;
    private CheckBox chkShowPasswordDoctor;
    private AppointmentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);

        dataSource = new AppointmentsDataSource(this);
        dataSource.open();

        etEmailDoctor = findViewById(R.id.etEmailDoctor);
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

    public void loginDoctor(View view) {
        String email = etEmailDoctor.getText().toString().trim();
        String password = etPasswordDoctor.getText().toString().trim();

        Doctor doctor = dataSource.getDoctorByEmailAndPassword(email, password);

        if (doctor != null) {
            showAlertDialog("Inicio de sesión exitoso", "Bienvenido " + doctor.getName());
        } else {
            showAlertDialog("Error", "Credenciales inválidas. Verifique su correo y contraseña.");
        }
    }

    public void goToDoctorRegister(View view) {
        Intent intent = new Intent(this, DoctorRegistrationActivity.class);
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
