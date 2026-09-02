package com.example.chargedoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chargedoctor.BaseActivity;

public class CableTestMain extends BaseActivity {

    Button btnDiagnosis;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cable_test_main);

        btnBack = findViewById(R.id.btnBack);
        btnDiagnosis = findViewById(R.id.btnDiagnosis);

        btnBack.setOnClickListener(v -> finish());
        btnDiagnosis.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            CableTestMain.this,
                            CableTestActivity.class);

            startActivity(intent);

        });
    }

}