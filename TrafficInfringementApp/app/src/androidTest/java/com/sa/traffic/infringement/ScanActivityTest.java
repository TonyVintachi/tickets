package com.sa.traffic.infringement;

import android.app.Activity;
import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.common.collect.Iterables;
import com.google.zxing.integration.android.IntentIntegrator;
import com.sa.traffic.infringement.models.Driver;
import com.sa.traffic.infringement.models.Vehicle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScanActivityTest {

    // Using ActivityScenarioRule is more modern than IntentsTestRule.
    // We will manage Intents.init() and Intents.release() manually.

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testDriverScan_parsesDataAndLaunchesDriverDetailsActivity() {
        Intent testIntent = new Intent(androidx.test.core.app.ApplicationProvider.getApplicationContext(), ScanActivity.class);
        testIntent.putExtra("SCAN_TYPE", "DRIVER_LICENSE");

        try (ActivityScenario<ScanActivity> scenario = ActivityScenario.launch(testIntent)) {
            String mockScanData = "Test Driver,12345ID,0801234567";
            Intent resultData = new Intent();
            // The key "SCAN_RESULT" is used by zxing-android-embedded library
            resultData.putExtra("SCAN_RESULT", mockScanData);
            resultData.putExtra("SCAN_RESULT_FORMAT", "QR_CODE"); // Format is also often included

            scenario.onActivity(activity -> {
                // Directly call onActivityResult.
                // The requestCode must match what IntentIntegrator uses.
                activity.onActivityResult(IntentIntegrator.REQUEST_CODE, Activity.RESULT_OK, resultData);
            });

            // Verify that an Intent was sent to DriverDetailsActivity
            intended(allOf(
                    hasComponent(DriverDetailsActivity.class.getName()),
                    hasExtraWithKey("DRIVER_OBJECT")
            ));

            // For deeper validation:
            Intent receivedIntent = Iterables.getOnlyElement(Intents.getIntents());
            Driver driver = receivedIntent.getParcelableExtra("DRIVER_OBJECT");
            assertNotNull(driver);
            assertEquals("Test Driver", driver.getName());
            assertEquals("12345ID", driver.getIdNumber());
            assertEquals("0801234567", driver.getContact());
            assertNotNull(driver.getOffenses());
            assertFalse(driver.getOffenses().isEmpty()); // ScanActivity adds mock offenses
            // Check one of the mock offenses added in ScanActivity
            assertEquals("Speeding Ticket", driver.getOffenses().get(0).getType());
        }
    }

    @Test
    public void testVehicleScan_parsesDataAndLaunchesVehicleDetailsActivity() {
        Intent testIntent = new Intent(androidx.test.core.app.ApplicationProvider.getApplicationContext(), ScanActivity.class);
        testIntent.putExtra("SCAN_TYPE", "VEHICLE_DISK");

        try (ActivityScenario<ScanActivity> scenario = ActivityScenario.launch(testIntent)) {
            String mockVehicleScanData = "TestMake,TestModel,XYZ123GP,Test Owner,Licensed";
            Intent resultData = new Intent();
            resultData.putExtra("SCAN_RESULT", mockVehicleScanData);
            resultData.putExtra("SCAN_RESULT_FORMAT", "QR_CODE");


            scenario.onActivity(activity -> {
                activity.onActivityResult(IntentIntegrator.REQUEST_CODE, Activity.RESULT_OK, resultData);
            });

            intended(allOf(
                    hasComponent(VehicleDetailsActivity.class.getName()),
                    hasExtraWithKey("VEHICLE_OBJECT")
            ));

            Intent receivedIntent = Iterables.getOnlyElement(Intents.getIntents());
            Vehicle vehicle = receivedIntent.getParcelableExtra("VEHICLE_OBJECT");
            assertNotNull(vehicle);
            assertEquals("TestMake", vehicle.getMake());
            assertEquals("TestModel", vehicle.getModel());
            assertEquals("XYZ123GP", vehicle.getRegistrationNumber());
            assertEquals("Test Owner", vehicle.getOwnerName());
            assertEquals("Licensed", vehicle.getStatus());
        }
    }
}
