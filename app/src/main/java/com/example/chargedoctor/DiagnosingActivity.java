package com.example.chargedoctor;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import com.example.chargedoctor.BaseActivity;
import java.util.ArrayList;

public class DiagnosingActivity extends BaseActivity{

    TextView tvCount;

    int currentMa;
    float temperature;

    ArrayList<Integer> currentList =
            new ArrayList<>();

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosing);

        tvCount = findViewById(R.id.tvCount);

        startDiagnosis();
    }

    private void startDiagnosis() {

        countDownTimer = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                int sec = (int)(millisUntilFinished / 1000);

                tvCount.setText(String.valueOf(sec));

                collectBatteryData();
                currentList.add(currentMa);
            }

            @Override
            public void onFinish() {
                goResult();
            }

        };
        countDownTimer.start();
    }

    private void collectBatteryData() {

        BatteryManager bm =
                (BatteryManager)getSystemService(BATTERY_SERVICE);

        int currentMicroA =
                bm.getIntProperty(
                        BatteryManager.BATTERY_PROPERTY_CURRENT_NOW
                );

        currentMa = Math.abs(currentMicroA / 1000);

        Intent batteryIntent =
                registerReceiver(
                        null,
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                );

        if(batteryIntent != null){

            int temp =
                    batteryIntent.getIntExtra(
                            BatteryManager.EXTRA_TEMPERATURE,
                            0
                    );

            temperature = temp / 10.0f;
        }
    }

    private void goResult() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        int sum = 0;

        for(int current : currentList){

            sum += current;

        }

        int averageCurrent = 0;

        if(!currentList.isEmpty()){

            averageCurrent =
                    sum / currentList.size();

        }

        int health =
                HealthAnalyzer.calculateHealth(
                        averageCurrent,
                        temperature
                );

        String status =
                HealthAnalyzer.getStatus(
                        health
                );

        Intent intent =
                new Intent(
                        DiagnosingActivity.this,
                        ResultActivity.class
                );

        intent.putExtra(
                "health",
                health
        );

        intent.putExtra(
                "current",
                averageCurrent
        );

        intent.putExtra(
                "temperature",
                temperature
        );

        intent.putExtra(
                "status",
                status
        );

        startActivity(intent);

        finish();
    }

    private void cancelDiagnosis() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        finish();
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("診断を中止")
                .setMessage("診断をキャンセルしますか？")
                .setPositiveButton("はい", (d, w) -> {
                    cancelDiagnosis();
                })
                .setNegativeButton("いいえ", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}