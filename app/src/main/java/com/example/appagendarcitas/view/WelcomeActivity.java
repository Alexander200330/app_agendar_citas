// WelcomeActivity.java
package com.example.appagendarcitas.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appagendarcitas.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        tvWelcomeMessage.setText(message);
    }
}
