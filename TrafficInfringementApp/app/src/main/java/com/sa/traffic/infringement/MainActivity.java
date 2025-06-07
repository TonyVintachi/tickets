package com.sa.traffic.infringement;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private Button buttonScanDriverLicense;
    private Button buttonScanVehicleDisk;
    private Button buttonViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Dashboard");
        }

        buttonScanDriverLicense = findViewById(R.id.buttonScanDriverLicense);
        buttonScanVehicleDisk = findViewById(R.id.buttonScanVehicleDisk);
        buttonViewHistory = findViewById(R.id.buttonViewHistory);

        buttonScanDriverLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra("SCAN_TYPE", "DRIVER_LICENSE");
                startActivity(intent);
            }
        });

        buttonScanVehicleDisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra("SCAN_TYPE", "VEHICLE_DISK");
                startActivity(intent);
            }
        });

        buttonViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "History feature not yet implemented.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
