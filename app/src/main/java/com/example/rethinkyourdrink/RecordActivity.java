package com.example.rethinkyourdrink;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RecordActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private Spinner categorySpinner;
    private TextInputEditText amountInput;
    private TextInputEditText beverageNameInput;
    private TextInputLayout beverageNameLayout;
    private RadioGroup dayRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record);

        dbHelper = new DatabaseHelper(this);

        // Initializw view
        categorySpinner = findViewById(R.id.categorySpinner);
        amountInput = findViewById(R.id.amountInput);
        beverageNameInput = findViewById(R.id.beverageNameInput);
        beverageNameLayout = findViewById(R.id.beverageNameLayout);
        dayRadioGroup = findViewById(R.id.dayRadioGroup);
        ExtendedFloatingActionButton saveButton = findViewById(R.id.saveButton);

        // Setup category spinner
        String[] categories = {"Plain Water", "Other Beverages (Non-sweetened)", "Other Beverages (Sweetened)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // SHow/hide beverage name input based on category selection
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                beverageNameLayout.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                beverageNameLayout.setVisibility(View.GONE);
            }
        });
            saveButton.setOnClickListener(v -> saveRecord());
    }

    private void saveRecord() {
        if (!validateInputs()) {
            return;
        }

        // Get selected day (1,2, or 3)
        int selectedDay;
        int checkedId = dayRadioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.day1Radio) {
            selectedDay = 1;
        } else if (checkedId == R.id.day2Radio) {
            selectedDay = 2;
        } else {
            selectedDay = 3;
        }
        // Get category and amount
        String category = categorySpinner.getSelectedItem().toString();
        int amount = Integer.parseInt(amountInput.getText().toString());

        // Get beverage name if applicable
        String beverageName = "";
        if (beverageNameLayout.getVisibility() == View.VISIBLE) {
            beverageName = beverageNameInput.getText().toString();
        }

        // Create new beverage record
        BeverageRecord record = new BeverageRecord(selectedDay, category, amount, beverageName);
        dbHelper.addBeverageRecord(record);

        // Return to main activity
        finish();
    }
    private boolean validateInputs() {
        if (dayRadioGroup.getCheckedRadioButtonId() == -1) {
            showError("Please select a day");
            return false;
        }

        String amountStr = amountInput.getText().toString();
        if (amountStr.isEmpty()) {
            showError("Please enter amount");
            return false;
        }

        if (beverageNameLayout.getVisibility() == View.VISIBLE &&
                beverageNameInput.getText().toString().isEmpty()) {
            showError("Please enter beverage name");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}