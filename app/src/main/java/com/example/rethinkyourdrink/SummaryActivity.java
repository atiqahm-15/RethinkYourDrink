package com.example.rethinkyourdrink;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private RecyclerView dailyTotalsRecyclerView;
    private RecyclerView categoryTotalsRecyclerView;
    private RecyclerView detailsRecyclerView;
    private TabLayout dayTabs;
    private DailyTotalAdapter dailyTotalAdapter;
    private CategoryTotalAdapter categoryTotalAdapter;
    private BeverageDetailAdapter beverageDetailAdapter;

    private int currentDay = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summary);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        dbHelper = new DatabaseHelper(this);

        // Initialize views
        dailyTotalsRecyclerView = findViewById(R.id.dailyTotalsRecyclerView);
        dayTabs = findViewById(R.id.dayTabs);
        categoryTotalsRecyclerView = findViewById(R.id.categoryTotalsRecyclerView);
        detailsRecyclerView = findViewById(R.id.detailsRecyclerView);

        // Setup RecyclerView
        setupRecyclerViews();

        // Setup TabLayout
        dayTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentDay = tab.getPosition() + 1;
                updateDisplayForDay(currentDay);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Initial display
        updateAllDisplays();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupRecyclerViews() {
        dailyTotalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryTotalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapters
        dailyTotalAdapter = new DailyTotalAdapter();
        categoryTotalAdapter = new CategoryTotalAdapter();
        beverageDetailAdapter = new BeverageDetailAdapter();

        // Set adapters
        dailyTotalsRecyclerView.setAdapter(dailyTotalAdapter);
        categoryTotalsRecyclerView.setAdapter(categoryTotalAdapter);
        detailsRecyclerView.setAdapter(beverageDetailAdapter);
    }

    private void updateAllDisplays() {
        // Update daily totals
        Map<Integer, Integer> dailyTotals = new HashMap<>();
        for (int day = 1; day <= 3; day++) {
            List<BeverageRecord> records = dbHelper.getRecordsForDay(day);
            int total = 0;
            for (BeverageRecord record : records) {
                total += record.getAmount();
            }
            dailyTotals.put(day, total);
        }

        // Update the daily totals adapter
        dailyTotalAdapter.setDailyTotals(dailyTotals);

        // Update current day display
        updateDisplayForDay(currentDay);
    }

    private void updateDisplayForDay(int day) {
        List<BeverageRecord> dayRecords = dbHelper.getRecordsForDay(day);

        // Calculate category totals
        Map<String, Integer> categoryTotals = new HashMap<>();
        for (BeverageRecord record : dayRecords) {
            String category = record.getCategory();
            categoryTotals.put(category,
                    categoryTotals.getOrDefault(category, 0) + record.getAmount());
        }
        // Update adapters with new data
        categoryTotalAdapter.setCategoryTotals(categoryTotals);
        beverageDetailAdapter.setBeverageRecords(dayRecords);
    }
}