package com.example.chargedoctor.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.example.chargedoctor.BaseActivity;
import com.example.chargedoctor.HomeActivity;
import com.example.chargedoctor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends BaseActivity {

    //SwitchCompat swAuto;
    LinearLayout layoutFontSetting, layoutLanguageSetting;
    TextView txtFontValue, txtLanguageValue;
    SharedPreferences prefs;

    int fontSize = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences("app_settings", MODE_PRIVATE);

        //swAuto = findViewById(R.id.swAuto);
        layoutFontSetting = findViewById(R.id.layoutFontSetting);
        layoutLanguageSetting = findViewById(R.id.layoutLanguageSetting);
        txtFontValue = findViewById(R.id.txtFontValue);
        txtLanguageValue = findViewById(R.id.txtLanguageValue);

        fontSize = prefs.getInt("fontSize", 17);
        String langCode = prefs.getString("language", "ja");

        txtFontValue.setText(fontSize + "sp ＞");
        txtLanguageValue.setText(getLanguageName(langCode) + " ＞");

        layoutFontSetting.setOnClickListener(v -> showFontDialog());
        layoutLanguageSetting.setOnClickListener(v -> showLanguageDialog());

//        swAuto.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            prefs.edit().putBoolean("autoDiagnosis", isChecked).apply();
//            Toast.makeText(this, getString(R.string.auto_diagnosis), Toast.LENGTH_SHORT).show();
//        });

        BottomNavigationView nav = findViewById(R.id.bottomNavigation);
        nav.setSelectedItemId(R.id.nav_settings);

        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }

            if (id == R.id.nav_history) {
                Intent intent = new Intent(SettingsActivity.this, HistoryActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            return id == R.id.nav_settings;
        });
    }

    private void showFontDialog() {
        String[] sizes = {
                "14sp",
                "17sp",
                "20sp",
                "24sp"
        };

        int[] values = {14, 17, 20, 24};

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.font_setting))
                .setItems(sizes, (dialog, which) -> {
                    fontSize = values[which];

                    prefs.edit()
                            .putInt("fontSize", fontSize)
                            .apply();

                    recreate(); // Settings画面のまま反映
                })
                .show();
    }

    private void showLanguageDialog() {
        String[] languages = {
                "日本語",
                "English",
                "မြန်မာ"
        };

        String[] codes = {
                "ja",
                "en",
                "my"
        };

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.language_setting))
                .setItems(languages, (dialog, which) -> {
                    prefs.edit()
                            .putString("language", codes[which])
                            .apply();

                    recreate(); // Homeへ行かずSettings画面だけ更新
                })
                .show();
    }

    private String getLanguageName(String code) {
        switch (code) {
            case "ja":
                return "日本語";
            case "en":
                return "English";
            case "my":
                return "မြန်မာ";
            default:
                return "日本語";
        }
    }
}