package com.sa.traffic.infringement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.sa.traffic.infringement.models.Driver;
import com.sa.traffic.infringement.models.Offense;
import java.util.List;

// Import JSONArray if you decide to parse a list of offenses, for now simplifying offenses_summary

public class DriverDetailsActivity extends AppCompatActivity {

    private TextView textViewDriverName;
    private TextView textViewDriverId;
    private TextView textViewDriverContact;
    private TextView textViewDriverOffenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        textViewDriverName = findViewById(R.id.textViewDriverName);
        textViewDriverId = findViewById(R.id.textViewDriverId);
        textViewDriverContact = findViewById(R.id.textViewDriverContact);
        textViewDriverOffenses = findViewById(R.id.textViewDriverOffenses);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("DRIVER_OBJECT")) {
            Driver driver = intent.getParcelableExtra("DRIVER_OBJECT");
            if (driver != null) {
                textViewDriverName.setText(driver.getName() != null ? driver.getName() : "N/A");
                textViewDriverId.setText(driver.getIdNumber() != null ? driver.getIdNumber() : "N/A");
                textViewDriverContact.setText(driver.getContact() != null ? driver.getContact() : "N/A");

                List<Offense> offenses = driver.getOffenses();
                if (offenses != null && !offenses.isEmpty()) {
                    StringBuilder offensesText = new StringBuilder();
                    for (Offense offense : offenses) {
                        offensesText.append("Type: ").append(offense.getType()).append("\n");
                        offensesText.append("Date: ").append(offense.getDate()).append("\n");
                        offensesText.append("Location: ").append(offense.getLocation()).append("\n");
                        offensesText.append("Amount: R").append(String.format("%.2f", offense.getAmount())).append("\n");
                        offensesText.append("Status: ").append(offense.getStatus()).append("\n\n");
                    }
                    textViewDriverOffenses.setText(offensesText.toString().trim());
                } else {
                    textViewDriverOffenses.setText("No outstanding offenses.");
                }
            } else {
                Toast.makeText(this, "No driver object received", Toast.LENGTH_SHORT).show();
                setFieldsToNoData();
            }
        } else {
            Toast.makeText(this, "No driver object passed", Toast.LENGTH_SHORT).show();
            setFieldsToNoData();
        }
    }

    private void setFieldsToNoData() {
        textViewDriverName.setText("No data received");
        textViewDriverId.setText("No data received");
        textViewDriverContact.setText("No data received");
        textViewDriverOffenses.setText("No data received");
    }
}
