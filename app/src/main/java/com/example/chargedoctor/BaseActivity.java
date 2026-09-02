package com.example.chargedoctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private String languageWhenCreated;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(
                LocaleManager.updateLocale(newBase)
        );
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs =
                getSharedPreferences(
                        "app_settings",
                        MODE_PRIVATE
                );

        languageWhenCreated =
                prefs.getString(
                        "language",
                        "ja"
                );
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs =
                getSharedPreferences(
                        "app_settings",
                        MODE_PRIVATE
                );
        int fontSize =
                prefs.getInt(
                        "fontSize",
                        17
                );

        String currentLanguage =
                prefs.getString(
                        "language",
                        "ja"
                );
        // ミャンマー語だけ少し小さくする
        if ("my".equals(currentLanguage)) {
            fontSize = Math.max(14, fontSize - 3);
        }

        if (!currentLanguage.equals(languageWhenCreated)) {

            languageWhenCreated = currentLanguage;

            recreate();

            return;
        }

        applyFontSize(getWindow().getDecorView());

    }

    private void applyFontSize(View view) {

        SharedPreferences prefs =
                getSharedPreferences(
                        "app_settings",
                        MODE_PRIVATE
                );

        int fontSize =
                prefs.getInt(
                        "fontSize",
                        17
                );

        if (view instanceof TextView) {

            ((TextView) view).setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    fontSize
            );

        }

        if (view instanceof ViewGroup) {

            ViewGroup group =
                    (ViewGroup) view;

            for (int i = 0; i < group.getChildCount(); i++) {

                applyFontSize(
                        group.getChildAt(i)
                );

            }

        }

    }

}