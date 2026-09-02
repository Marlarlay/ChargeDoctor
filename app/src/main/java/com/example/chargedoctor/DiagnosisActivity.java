package com.example.chargedoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.chargedoctor.BaseActivity;

public class DiagnosisActivity extends BaseActivity {

    LinearLayout cableTest;
    LinearLayout shakeTest;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();   // 前の画面に戻る
        });

        cableTest = findViewById(R.id.cableTest);
        shakeTest = findViewById(R.id.shakeTest);

        cableTest.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DiagnosisActivity.this,
                            MainActivity.class
                    );

            startActivity(intent);

        });

        shakeTest.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DiagnosisActivity.this,
                            CableTestMain.class
                    );

            startActivity(intent);
        });
    }
}