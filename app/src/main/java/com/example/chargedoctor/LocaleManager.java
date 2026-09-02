package com.example.chargedoctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LocaleManager {

    public static Context updateLocale(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(
                        "app_settings",
                        Context.MODE_PRIVATE
                );

        String language =
                prefs.getString(
                        "language",
                        "ja"
                );

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config =
                new Configuration(
                        context.getResources().getConfiguration()
                );

        config.setLocale(locale);

        return context.createConfigurationContext(config);
    }
}