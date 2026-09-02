package com.example.chargedoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.app.AlertDialog;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.example.chargedoctor.BaseActivity;

public class MainActivity extends BaseActivity {

    Button btnStart;
    private ImageButton btnBack;
    private AlertDialog waitingDialog;
    private Handler waitHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();   // 前の画面に戻る
        });

        btnStart = findViewById(R.id.btnStart);

        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {

            if (isCableConnected()) {

                Intent intent =
                        new Intent(
                                MainActivity.this,
                                DiagnosingActivity.class);

                startActivity(intent);

            } else {

                showWaitingDialog();
            }

        });
    }
    private boolean isCableConnected() {

        BatteryManager bm =
                (BatteryManager) getSystemService(BATTERY_SERVICE);

        return bm.isCharging();
    }

    private void showWaitingDialog() {

        waitingDialog = new AlertDialog.Builder(this)
                .setTitle("ケーブル未接続")
                .setMessage("ケーブルを接続してください")
                .setCancelable(false)
                .setNegativeButton("キャンセル", (dialog, which) -> {
                    waitHandler.removeCallbacksAndMessages(null);
                    dialog.dismiss();
                })
                .show();

        waitForCable();
    }

    private void waitForCable() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (isCableConnected()) {

                    waitHandler.removeCallbacksAndMessages(null);

                    if (waitingDialog != null) {
                        waitingDialog.dismiss();
                    }

                    Intent intent =
                            new Intent(
                                    MainActivity.this,
                                    DiagnosingActivity.class);

                    startActivity(intent);

                    return;

                } else {

                    waitHandler.postDelayed(this, 1000);
                }
            }
        };

        waitHandler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        waitHandler.removeCallbacksAndMessages(null);
    }
}