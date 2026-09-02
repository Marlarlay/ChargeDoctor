package com.example.chargedoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.chargedoctor.BaseActivity;

import com.example.chargedoctor.activities.HistoryActivity;
import com.example.chargedoctor.activities.SettingsActivity;

public class HomeActivity extends BaseActivity  {

    Button btnDiagnosis;
    Button btnHistory;
    Button btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnDiagnosis = findViewById(R.id.btnDiagnosis);
        btnHistory = findViewById(R.id.btnHistory);
        btnSetting = findViewById(R.id.btnSetting);

        // 診断画面へ
        btnDiagnosis.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            CarefulActivity.class
                    );

            startActivity(intent);

        });

        // 履歴画面へ
        btnHistory.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            HistoryActivity.class
                    );

            startActivity(intent);

        });

        // 設定画面へ
        btnSetting.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);

        });
    }
}