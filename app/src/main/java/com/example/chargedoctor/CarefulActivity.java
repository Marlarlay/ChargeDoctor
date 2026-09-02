package com.example.chargedoctor;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chargedoctor.DiagnosisActivity;

public class CarefulActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careful);

        Button button = findViewById(R.id.btnStartDiagnosis);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(CarefulActivity.this, DiagnosisActivity.class);
            startActivity(intent);
            finish();
        });
    }
}