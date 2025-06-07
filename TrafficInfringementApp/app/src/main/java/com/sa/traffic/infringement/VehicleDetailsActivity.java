package com.sa.traffic.infringement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.sa.traffic.infringement.models.Vehicle;

public class VehicleDetailsActivity extends AppCompatActivity {

    private TextView textViewVehicleMake;
    private TextView textViewVehicleModel;
    private TextView textViewVehicleReg;
    private TextView textViewVehicleOwner;
    private TextView textViewVehicleStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Vehicle Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textViewVehicleMake = findViewById(R.id.textViewVehicleMake);
        textViewVehicleModel = findViewById(R.id.textViewVehicleModel);
        textViewVehicleReg = findViewById(R.id.textViewVehicleReg);
        textViewVehicleOwner = findViewById(R.id.textViewVehicleOwner);
        textViewVehicleStatus = findViewById(R.id.textViewVehicleStatus);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("VEHICLE_OBJECT")) {
            Vehicle vehicle = intent.getParcelableExtra("VEHICLE_OBJECT");
            if (vehicle != null) {
                textViewVehicleMake.setText(vehicle.getMake() != null ? vehicle.getMake() : "N/A");
                textViewVehicleModel.setText(vehicle.getModel() != null ? vehicle.getModel() : "N/A");
                textViewVehicleReg.setText(vehicle.getRegistrationNumber() != null ? vehicle.getRegistrationNumber() : "N/A");
                textViewVehicleOwner.setText(vehicle.getOwnerName() != null ? vehicle.getOwnerName() : "N/A");
                if (vehicle.getStatus() != null) {
                    textViewVehicleStatus.setText(vehicle.getStatus());
                } else {
                    textViewVehicleStatus.setText("N/A");
                }
            } else {
                Toast.makeText(this, "No vehicle object received", Toast.LENGTH_SHORT).show();
                setFieldsToNoData();
            }
        } else {
            Toast.makeText(this, "No vehicle object passed", Toast.LENGTH_SHORT).show();
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
