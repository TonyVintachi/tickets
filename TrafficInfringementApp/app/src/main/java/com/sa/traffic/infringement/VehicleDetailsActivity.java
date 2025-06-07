package com.sa.traffic.infringement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class VehicleDetailsActivity extends AppCompatActivity {

    private TextView textViewVehicleMake;
    private TextView textViewVehicleModel;
    private TextView textViewVehicleReg;
    private TextView textViewVehicleOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        textViewVehicleMake = findViewById(R.id.textViewVehicleMake);
        textViewVehicleModel = findViewById(R.id.textViewVehicleModel);
        textViewVehicleReg = findViewById(R.id.textViewVehicleReg);
        textViewVehicleOwner = findViewById(R.id.textViewVehicleOwner);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("VEHICLE_DATA")) {
            String vehicleDataJson = intent.getStringExtra("VEHICLE_DATA");
            if (vehicleDataJson != null) {
                try {
                    // Mock vehicle JSON structure:
                    // {"make": "Toyota", "model": "Corolla", "registration": "CA 123-456", "ownerName": "Jane Doe"}
                    JSONObject jsonObject = new JSONObject(vehicleDataJson);

                    String make = jsonObject.optString("make", "N/A");
                    String model = jsonObject.optString("model", "N/A");
                    String registration = jsonObject.optString("registration", "N/A");
                    String ownerName = jsonObject.optString("ownerName", "N/A");

                    textViewVehicleMake.setText(make);
                    textViewVehicleModel.setText(model);
                    textViewVehicleReg.setText(registration);
                    textViewVehicleOwner.setText(ownerName);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error parsing vehicle data", Toast.LENGTH_SHORT).show();
                    setFieldsToNoData();
                }
            } else {
                Toast.makeText(this, "No vehicle data received", Toast.LENGTH_SHORT).show();
                setFieldsToNoData();
            }
        } else {
            Toast.makeText(this, "No vehicle data passed", Toast.LENGTH_SHORT).show();
            setFieldsToNoData();
        }
    }

    private void setFieldsToNoData() {
        textViewVehicleMake.setText("No data received");
        textViewVehicleModel.setText("No data received");
        textViewVehicleReg.setText("No data received");
        textViewVehicleOwner.setText("No data received");
    }
}
