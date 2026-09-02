package com.example.chargedoctor;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.chargedoctor.BaseActivity;

import com.example.chargedoctor.R;

public class HistoryDetailActivity extends BaseActivity {

    TextView tvDate;
    TextView tvHealth;
    TextView tvCurrent;
    TextView tvTemp;
    TextView tvStatus;
    TextView tvCableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        tvDate = findViewById(R.id.tvDate);
        tvHealth = findViewById(R.id.tvHealth);
        tvCurrent = findViewById(R.id.tvCurrent);
        tvTemp = findViewById(R.id.tvTemp);
        tvStatus = findViewById(R.id.tvStatus);
        tvCableName = findViewById(R.id.tvCableName);
        ImageView btnBack =
                findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();
        });

        tvDate.setText(
                getIntent().getStringExtra("date")
        );

        tvHealth.setText(
                getIntent().getIntExtra("health", 0)
                        + "%"
        );

        tvCurrent.setText(
                getIntent().getIntExtra("current", 0)
                        + " mA"
        );

        tvTemp.setText(
                getIntent().getFloatExtra("temp", 0)
                        + " ℃"
        );

        tvStatus.setText(
                getIntent().getStringExtra("status")
        );

        tvCableName.setText(
                getIntent().getStringExtra("cableName")
        );
    }
}