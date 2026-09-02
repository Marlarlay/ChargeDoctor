package com.example.chargedoctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.app.AlertDialog;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.os.BatteryManager;
import java.util.ArrayList;
import java.util.Collections;


public class CableTestActivity extends BaseActivity {

    ProgressBar progressBar;
    Handler handler = new Handler();
    Handler waitHandler = new Handler(Looper.getMainLooper());
    int progress = 0;
    ArrayList<Integer> currents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cable_test);

        progressBar = findViewById(R.id.progressBar);

        if (!isCableConnected()) {
            showWaitingDialog();
        } else {
            startAutoTest();
        }
    }

    private void startAutoTest() {
        progress = 0;
        currents.clear();
        progressBar.setProgress(0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isCableConnected()) {

                    handler.removeCallbacksAndMessages(null);

                    Toast.makeText(
                            CableTestActivity.this,
                            "ケーブルが抜かれました",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                    return;
                }

                progress += 10;
                progressBar.setProgress(progress);

                BatteryManager bm =
                        (BatteryManager)getSystemService(
                                BATTERY_SERVICE
                        );

                int current =
                        bm.getIntProperty(
                                BatteryManager.BATTERY_PROPERTY_CURRENT_NOW
                        );

                current = Math.abs(current / 1000);

                currents.add(current);

                android.util.Log.d(
                        "ChargeDoctor",
                        "current = " + current + "mA"
                );

                if (progress < 100) {
                    handler.postDelayed(this, 1000);
                } else {
                    int max = Collections.max(currents);
                    int min = Collections.min(currents);

                    int diff = max - min;

                    int health;

                    if(diff <= 100){

                        health = 95;

                    }else if(diff <= 300){

                        health = 80;

                    }else if(diff <= 600){

                        health = 60;

                    }else{

                        health = 30;
                    }

                    String status;

                    if(health >= 90){

                        status = "安全";

                    }else if(health >= 70){

                        status = "注意";

                    }else{

                        status = "危険";
                    }

                    Intent intent = new Intent(
                            CableTestActivity.this,
                            ResultActivity2.class
                    );

                    intent.putExtra("health", health);
                    intent.putExtra("status", status);

                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }
    private boolean isCableConnected() {


        BatteryManager bm =
                (BatteryManager) getSystemService(BATTERY_SERVICE);

        return bm.isCharging();
    }
    private AlertDialog waitingDialog;

    private void showWaitingDialog() {

        waitingDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.cable_not_connected))
                .setMessage(getString(R.string.connect_cable))
                .setCancelable(false)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    cancelDiagnosis();
                })
                .show();

        waitForCable();
    }

    private void waitForCable() {

        Runnable runnable =
                new Runnable() {

                    @Override
                    public void run() {

                        if (isCableConnected()) {

                            if (waitingDialog != null) {
                                waitingDialog.dismiss();
                            }

                            startAutoTest();
                            return;

                        } else {

                            waitHandler.postDelayed(
                                    this,
                                    1000
                            );
                        }
                    }
                };

        waitHandler.post(runnable);
    }

    private void cancelDiagnosis() {

        handler.removeCallbacksAndMessages(null);
        waitHandler.removeCallbacksAndMessages(null);

        if (waitingDialog != null && waitingDialog.isShowing()) {
            waitingDialog.dismiss();
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

        handler.removeCallbacksAndMessages(null);
        waitHandler.removeCallbacksAndMessages(null);
    }
}