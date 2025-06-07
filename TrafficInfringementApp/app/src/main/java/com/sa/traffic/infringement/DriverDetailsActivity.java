package com.sa.traffic.infringement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
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
        if (intent != null && intent.hasExtra("DRIVER_DATA")) {
            String driverDataJson = intent.getStringExtra("DRIVER_DATA");
            if (driverDataJson != null) {
                try {
                    // Simplified mockDriverJson structure:
                    // {"name": "John Doe", "idNumber": "1234567890123", "contact": "0821234567", "offenses_summary": "Speeding - R500"}
                    // For the offenses array in previous mock:
                    // JSONArray offensesArray = jsonObject.getJSONArray("offenses");
                    // if (offensesArray.length() > 0) {
                    //    JSONObject firstOffense = offensesArray.getJSONObject(0);
                    //    offensesSummary = firstOffense.getString("type") + " (" + firstOffense.getString("date") + ") - Amount: " + firstOffense.getInt("amount");
                    // }
                    // For this example, we'll use a simplified "offenses_summary" string directly if present in mock,
                    // or construct from the more complex one if needed.
                    // The mock data from ScanActivity is:
                    // "{\"name\": \"John Doe\", \"idNumber\": \"1234567890123\", \"contact\": \"0821234567\", \"offenses\": [{\"type\": \"Speeding\", \"date\": \"2023-10-26\", \"amount\": 500}]}"

                    JSONObject jsonObject = new JSONObject(driverDataJson);

                    String name = jsonObject.optString("name", "N/A");
                    String idNumber = jsonObject.optString("idNumber", "N/A");
                    String contact = jsonObject.optString("contact", "N/A");

                    // Handling the offenses array
                    String offensesSummary = "No outstanding offenses"; // Default
                    if (jsonObject.has("offenses")) {
                        org.json.JSONArray offensesArray = jsonObject.getJSONArray("offenses");
                        if (offensesArray.length() > 0) {
                            // For simplicity, just show the first offense's type and date
                            JSONObject firstOffense = offensesArray.getJSONObject(0);
                            offensesSummary = firstOffense.optString("type", "N/A") + " (" + firstOffense.optString("date", "N/A") + ")";
                        }
                    } else if (jsonObject.has("offenses_summary")) { // Fallback for simplified version
                        offensesSummary = jsonObject.optString("offenses_summary", "N/A");
                    }

                    textViewDriverName.setText(name);
                    textViewDriverId.setText(idNumber);
                    textViewDriverContact.setText(contact);
                    textViewDriverOffenses.setText(offensesSummary);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error parsing driver data", Toast.LENGTH_SHORT).show();
                    setFieldsToNoData();
                }
            } else {
                Toast.makeText(this, "No driver data received", Toast.LENGTH_SHORT).show();
                setFieldsToNoData();
            }
        } else {
            Toast.makeText(this, "No driver data passed", Toast.LENGTH_SHORT).show();
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
