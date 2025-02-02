package com.example.rethinkyourdrink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView totalWaterText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        totalWaterText = findViewById(R.id.totalWaterText);
        Button recordButton = findViewById(R.id.recordButton);
        Button summaryButton = findViewById(R.id.summaryButton);

        recordButton.setOnClickListener(v -> {
            // Hand record button click
            Intent intent = new Intent(this, RecordActivity.class);
            startActivity(intent);
        });

        summaryButton.setOnClickListener(v -> {
            // Hand summary button click
            Intent intent = new Intent(this, SummaryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTodayTotal();
    }

    private void updateTodayTotal() {
        List<BeverageRecord> todayRecords = dbHelper.getRecordsForDay(1);
        int totalWater = 0;
        for (BeverageRecord record : todayRecords) {
            totalWater += record.getAmount();
        }
        totalWaterText.setText(String.format("%d ml", totalWater));
    }
}