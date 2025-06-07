package com.sa.traffic.infringement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class ScanActivity extends AppCompatActivity {

    private Button buttonLaunchScanner;
    private TextView textViewScanPrompt;
    private String scanType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        buttonLaunchScanner = findViewById(R.id.buttonLaunchScanner);
        textViewScanPrompt = findViewById(R.id.textViewScanPrompt);

        // Retrieve the scan type passed from MainActivity
        Intent currentIntent = getIntent();
        if (currentIntent != null && currentIntent.hasExtra("SCAN_TYPE")) {
            scanType = currentIntent.getStringExtra("SCAN_TYPE");
            // You can update UI or behavior based on scanType if needed
            // For example, update the prompt:
            if ("DRIVER_LICENSE".equals(scanType)) {
                textViewScanPrompt.setText("Point camera at Driver License barcode/QR code");
            } else if ("VEHICLE_DISK".equals(scanType)) {
                textViewScanPrompt.setText("Point camera at Vehicle Disk barcode/QR code");
            }
        } else {
            // Default or error handling
            scanType = "UNKNOWN";
            textViewScanPrompt.setText("Scan target not specified.");
        }


        buttonLaunchScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder for launching scanner or simulating scan
                String mockDriverJson = "{\"name\": \"John Doe\", \"idNumber\": \"1234567890123\", \"contact\": \"0821234567\", \"offenses\": [{\"type\": \"Speeding\", \"date\": \"2023-10-26\", \"amount\": 500}]}";
                String mockVehicleJson = "{\"make\": \"Toyota\", \"model\": \"Corolla\", \"registration\": \"CA 123-456\", \"ownerName\": \"Jane Doe\"}";

                if ("DRIVER_LICENSE".equals(scanType)) {
                    Intent intent = new Intent(ScanActivity.this, DriverDetailsActivity.class);
                    intent.putExtra("DRIVER_DATA", mockDriverJson);
                    startActivity(intent);
                } else if ("VEHICLE_DISK".equals(scanType)) {
                    Intent intent = new Intent(ScanActivity.this, VehicleDetailsActivity.class);
                    intent.putExtra("VEHICLE_DATA", mockVehicleJson);
                    startActivity(intent);
                } else {
                    Toast.makeText(ScanActivity.this, "Unknown scan type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
