package com.sa.traffic.infringement;

import android.os.Bundle;
import android.view.View;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sa.traffic.infringement.models.Driver;
import com.sa.traffic.infringement.models.Offense;
import com.sa.traffic.infringement.models.Vehicle;

public class ScanActivity extends AppCompatActivity {

    private Button buttonLaunchScanner;
    private TextView textViewScanPrompt;
    private String scanType;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

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
                initiateScan();
            }
        });
    }

    private void initiateScan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActualScan();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    private void startActualScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode or QR code");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true); // If you want the image
        integrator.setOrientationLocked(false); // Allow orientation changes
        integrator.initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                startActualScan();
            } else {
                Toast.makeText(this, "Camera permission denied. Cannot scan.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                // For now, just display the raw scanned content in a Toast
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                String scannedContent = result.getContents();
                scanType = getIntent().getStringExtra("SCAN_TYPE"); // Ensure scanType is current

                if (scannedContent == null || scannedContent.isEmpty()) {
                    Toast.makeText(this, "Scanned content is empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("DRIVER_LICENSE".equals(scanType)) {
                    Driver driver = parseDriverData(scannedContent);
                    Offense mockOffense = new Offense("Speeding Ticket", "2024-01-15", "Main Road", 250.00, "Outstanding");
                    driver.addOffense(mockOffense);
                    // Add another mock offense for demonstration
                    Offense mockOffense2 = new Offense("Illegal Parking", "2023-12-01", "Downtown", 100.00, "Paid");
                    driver.addOffense(mockOffense2);


                    Intent driverIntent = new Intent(this, DriverDetailsActivity.class);
                    driverIntent.putExtra("DRIVER_OBJECT", driver); // Pass Parcelable Driver object
                    startActivity(driverIntent);

                } else if ("VEHICLE_DISK".equals(scanType)) {
                    Vehicle vehicle = parseVehicleData(scannedContent);
                    Intent vehicleIntent = new Intent(this, VehicleDetailsActivity.class);
                    vehicleIntent.putExtra("VEHICLE_OBJECT", vehicle); // Pass Parcelable Vehicle object
                    startActivity(vehicleIntent);

                } else {
                    Toast.makeText(this, "Unknown scan type: " + scanType, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Simulated parsing for Driver Data
    private Driver parseDriverData(String content) {
        // Example format: "John Doe,1234567890123,0821112233"
        String[] parts = content.split(",");
        Driver driver = new Driver(); // Uses default constructor
        if (parts.length >= 1) driver.setName(parts[0].trim()); else driver.setName("N/A");
        if (parts.length >= 2) driver.setIdNumber(parts[1].trim()); else driver.setIdNumber(content); // Fallback
        if (parts.length >= 3) driver.setContact(parts[2].trim()); else driver.setContact("N/A");
        // Offenses will be added separately or could be part of a more complex string
        return driver;
    }

    // Simulated parsing for Vehicle Data
    private Vehicle parseVehicleData(String content) {
        // Example format: "Toyota,Corolla,CA123456,Jane Doe,Licensed"
        String[] parts = content.split(",");
        Vehicle vehicle = new Vehicle(); // Uses default constructor
        if (parts.length >= 1) vehicle.setMake(parts[0].trim()); else vehicle.setMake("N/A");
        if (parts.length >= 2) vehicle.setModel(parts[1].trim()); else vehicle.setModel("N/A");
        if (parts.length >= 3) vehicle.setRegistrationNumber(parts[2].trim()); else vehicle.setRegistrationNumber(content); // Fallback
        if (parts.length >= 4) vehicle.setOwnerName(parts[3].trim()); else vehicle.setOwnerName("N/A");
        if (parts.length >= 5) vehicle.setStatus(parts[4].trim()); else vehicle.setStatus("Unknown");
        return vehicle;
    }
}
