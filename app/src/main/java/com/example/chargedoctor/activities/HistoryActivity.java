package com.example.chargedoctor.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.example.chargedoctor.BaseActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chargedoctor.BaseActivity;
import com.example.chargedoctor.R;
import com.example.chargedoctor.HomeActivity;
import com.example.chargedoctor.adapter.HistoryAdapter;
import com.example.chargedoctor.model.HistoryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<HistoryItem> historyList;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();   // 前の画面に戻る
        });

        recyclerView = findViewById(R.id.recyclerHistory);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        historyList = new ArrayList<>();

        SharedPreferences pref =
                getSharedPreferences(
                        "history",
                        MODE_PRIVATE
                );

        String history =
                pref.getString(
                        "history_data",
                        ""
                );

        String[] lines =
                history.split("\n");

        for(String line : lines){

            if(line.isEmpty()) continue;

            String[] data =
                    line.split(",");

            if(data.length >= 7){

                historyList.add(
                        new HistoryItem(
                                data[0],
                                Integer.parseInt(data[1]),
                                Integer.parseInt(data[2]),
                                Float.parseFloat(data[3]),
                                data[4],
                                data[5],
                                data[6]
                        )
                );
            }
        }

        List<HistoryItem> chargeList =
                new ArrayList<>();

        List<HistoryItem> shakeList =
                new ArrayList<>();

        for(HistoryItem item : historyList){

            if(item.getType().equals("CHARGE")){

                chargeList.add(item);

            } else if(item.getType().equals("SHAKE")){

                shakeList.add(item);
            }
        }

        adapter =
                new HistoryAdapter(
                        chargeList
                );

        recyclerView.setAdapter(
                adapter
        );

        TabLayout tabLayout =
                findViewById(R.id.tabLayout);

        tabLayout.addTab(
                tabLayout.newTab().setText(getString(R.string.charge_diagnosis))
        );

        tabLayout.addTab(
                tabLayout.newTab().setText(getString(R.string.cable_test))
        );

        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {

                    @Override
                    public void onTabSelected(
                            TabLayout.Tab tab) {

                        if(tab.getPosition() == 0){

                            adapter =
                                    new HistoryAdapter(
                                            chargeList
                                    );

                        }else{

                            adapter =
                                    new HistoryAdapter(
                                            shakeList
                                    );
                        }

                        recyclerView.setAdapter(
                                adapter
                        );
                    }

                    @Override
                    public void onTabUnselected(
                            TabLayout.Tab tab) {}

                    @Override
                    public void onTabReselected(
                            TabLayout.Tab tab) {}
                });

        // Bottom Navigation
        BottomNavigationView nav =
                findViewById(R.id.bottomNavigation);

        nav.setSelectedItemId(R.id.nav_history);

        nav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                Intent intent =
                        new Intent(
                                HistoryActivity.this,
                                HomeActivity.class);

                startActivity(intent);
                return true;
            }

            if (id == R.id.nav_settings) {

                Intent intent =
                        new Intent(
                                HistoryActivity.this,
                                SettingsActivity.class);

                startActivity(intent);
                return true;
            }

            if (id == R.id.nav_history) {
                return true;
            }

            return false;
        });
    }
}