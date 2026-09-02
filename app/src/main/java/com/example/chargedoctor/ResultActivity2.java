package com.example.chargedoctor;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.chargedoctor.BaseActivity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.chargedoctor.activities.HistoryActivity;

public class ResultActivity2 extends BaseActivity {

    Button btnSave;
    TextView txtResult;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        int health =
                getIntent().getIntExtra(
                        "health",
                        0
                );

        String status =
                getIntent().getStringExtra(
                        "status"
                );

        btnSave = findViewById(R.id.btnSave);
        txtResult = findViewById(R.id.txtResult);

        txtResult.setText(
                "健康度 " + health + "% (" + status + ")"
        );

        btnSave.setOnClickListener(v -> {

            EditText editText = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle("ケーブル名")
                    .setMessage("ケーブル名を入力してください")
                    .setView(editText)
                    .setPositiveButton("保存", (dialog, which) -> {
                        String cableName = editText.getText().toString();

                        String date =
                                new SimpleDateFormat(
                                        "yyyy/MM/dd",
                                        Locale.getDefault())
                                        .format(new Date());

                        String time =
                                new SimpleDateFormat(
                                        "HH:mm:ss",
                                        Locale.getDefault())
                                        .format(new Date());

                        String result =
                                txtResult.getText().toString();

                        SharedPreferences prefs =
                                getSharedPreferences(
                                        "history",
                                        MODE_PRIVATE);

                        String oldHistory =
                                prefs.getString(
                                        "history_data",
                                        "");

                        String newItem =
                                date + " " + time + "," +
                                        health + "," +
                                        -1 + "," +
                                        -1 + "," +
                                        status + "," +
                                        cableName + "," +
                                        "SHAKE\n";

                        prefs.edit()
                                .putString(
                                        "history_data",
                                        newItem + oldHistory
                                )
                                .apply();

                        Intent intent =
                                new Intent(
                                        ResultActivity2.this,
                                        HistoryActivity.class
                                );

                        startActivity(intent);
                        finish();


                    })
                    .setNegativeButton("キャンセル", null)
                    .show();
        });
    }
}