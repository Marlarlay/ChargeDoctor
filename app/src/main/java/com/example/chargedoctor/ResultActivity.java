package com.example.chargedoctor;

import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Bundle;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import com.example.chargedoctor.BaseActivity;

public class ResultActivity extends BaseActivity {

    TextView tvHealth;
    TextView tvStatus;
    TextView tvCurrent;
    TextView tvTemp;
    TextView tvMessage;
    Button btnHome;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvHealth = findViewById(R.id.tvHealth);
        tvStatus = findViewById(R.id.tvStatus);
        tvCurrent = findViewById(R.id.tvCurrent);
        tvTemp = findViewById(R.id.tvTemp);
        tvMessage = findViewById(R.id.tvMessage);
        btnHome = findViewById(R.id.btnHome);
        btnSave = findViewById(R.id.btnSave);

        int health =
                getIntent().getIntExtra(
                        "health",
                        0
                );

        int current =
                getIntent().getIntExtra(
                        "current",
                        0
                );

        float temp =
                getIntent().getFloatExtra(
                        "temperature",
                        0
                );

        String status =
                getIntent().getStringExtra(
                        "status"
                );

        tvHealth.setText(
                health + "%"
        );

        tvStatus.setText(
                "状態 : " + status
        );

        tvCurrent.setText(
                current + " mA"
        );

        tvTemp.setText(
                temp + " ℃"
        );

        if(health >= 90){

            tvMessage.setText(
                    "新品同様の状態です"
            );

            tvHealth.setTextColor(
                    android.graphics.Color.parseColor("#4CAF50")
            );

            tvStatus.setTextColor(
                    android.graphics.Color.parseColor("#4CAF50")
            );

            tvMessage.setTextColor(
                    android.graphics.Color.parseColor("#4CAF50")
            );

        }else if(health >= 70){

            tvMessage.setText(
                    "問題なく使用できます"
            );

            tvHealth.setTextColor(
                    android.graphics.Color.parseColor("#8BC34A")
            );

            tvStatus.setTextColor(
                    android.graphics.Color.parseColor("#8BC34A")
            );

            tvMessage.setTextColor(
                    android.graphics.Color.parseColor("#8BC34A")
            );

        }else if(health >= 50){

            tvMessage.setText(
                    "劣化の兆候があります"
            );

            tvHealth.setTextColor(
                    android.graphics.Color.parseColor("#FFC107")
            );

            tvStatus.setTextColor(
                    android.graphics.Color.parseColor("#FFC107")
            );

            tvMessage.setTextColor(
                    android.graphics.Color.parseColor("#FFC107")
            );

        }else{

            tvMessage.setText(
                    "交換を推奨します"
            );

            tvHealth.setTextColor(
                    android.graphics.Color.parseColor("#F44336")
            );

            tvStatus.setTextColor(
                    android.graphics.Color.parseColor("#F44336")
            );

            tvMessage.setTextColor(
                    android.graphics.Color.parseColor("#F44336")
            );
        }
        btnHome.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ResultActivity.this,
                            HomeActivity.class
                    );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);
            finish();

        });

        btnSave.setOnClickListener(v -> {

            EditText editCable =
                    new EditText(
                            ResultActivity.this
                    );

            editCable.setHint(
                    "ケーブル名"
            );

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(
                            ResultActivity.this
                    );

            builder.setTitle(
                    "ケーブル名を入力"
            );

            builder.setView(
                    editCable
            );

            builder.setNegativeButton(
                    "キャンセル",
                    (dialog, which) -> dialog.dismiss()
            );

            builder.setPositiveButton(
                    "保存",
                    (dialog, which) -> {

                        String cableName =
                                editCable.getText()
                                        .toString()
                                        .trim();

                        if(cableName.isEmpty()){

                            cableName = "未設定";

                        }

                        SharedPreferences pref =
                                getSharedPreferences(
                                        "history",
                                        MODE_PRIVATE
                                );

                        String oldHistory =
                                pref.getString(
                                        "history_data",
                                        ""
                                );

                        String date =
                                new SimpleDateFormat(
                                        "yyyy/MM/dd HH:mm",
                                        Locale.JAPAN
                                ).format(new Date());

                        String newData =
                                date + "," +
                                        health + "," +
                                        current + "," +
                                        temp + "," +
                                        status + "," +
                                        cableName + "," +
                                        "CHARGE\n";

                        pref.edit()
                                .putString(
                                        "history_data",
                                        newData + oldHistory
                                )
                                .apply();

                        Toast.makeText(
                                ResultActivity.this,
                                "保存しました",
                                Toast.LENGTH_SHORT
                        ).show();

                        Intent intent =
                                new Intent(
                                        ResultActivity.this,
                                        com.example.chargedoctor.activities.HistoryActivity.class
                                );

                        startActivity(intent);

                        finish();
                    }
            );

            builder.show();
        });
    }
}