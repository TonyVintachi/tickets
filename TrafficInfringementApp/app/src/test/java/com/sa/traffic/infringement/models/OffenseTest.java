package com.sa.traffic.infringement.models;

import android.os.Parcel;
import org.junit.Test;
import static org.junit.Assert.*;

public class OffenseTest {

    @Test
    public void constructor_initializesFieldsCorrectly() {
        String type = "Speeding";
        String date = "2024-03-15";
        String location = "N1 Highway";
        double amount = 500.00;
        String status = "Outstanding";

        Offense offense = new Offense(type, date, location, amount, status);

        assertEquals(type, offense.getType());
        assertEquals(date, offense.getDate());
        assertEquals(location, offense.getLocation());
        assertEquals(amount, offense.getAmount(), 0.001);
        assertEquals(status, offense.getStatus());
    }

    @Test
    public void gettersAndSetters_workCorrectly() {
        Offense offense = new Offense("Initial Type", "Initial Date", "Initial Location", 100.0, "Initial Status");

        String newType = "Parking Violation";
        offense.setType(newType);
        assertEquals(newType, offense.getType());

        String newDate = "2024-03-16";
        offense.setDate(newDate);
        assertEquals(newDate, offense.getDate());

        String newLocation = "Main Street";
        offense.setLocation(newLocation);
        assertEquals(newLocation, offense.getLocation());

        double newAmount = 75.50;
        offense.setAmount(newAmount);
        assertEquals(newAmount, offense.getAmount(), 0.001);

        String newStatus = "Paid";
        offense.setStatus(newStatus);
        assertEquals(newStatus, offense.getStatus());
    }

    @Test
    public void parcelableImplementation_isCorrect() {
        Offense originalOffense = new Offense("Illegal U-Turn", "2024-03-17", "Crossroads", 150.00, "Appealed");

        Parcel parcel = Parcel.obtain();
        originalOffense.writeToParcel(parcel, originalOffense.describeContents());
        parcel.setDataPosition(0);

        Offense createdFromParcel = Offense.CREATOR.createFromParcel(parcel);

        assertNotNull(createdFromParcel);
        assertEquals(originalOffense.getType(), createdFromParcel.getType());
        assertEquals(originalOffense.getDate(), createdFromParcel.getDate());
        assertEquals(originalOffense.getLocation(), createdFromParcel.getLocation());
        assertEquals(originalOffense.getAmount(), createdFromParcel.getAmount(), 0.001);
        assertEquals(originalOffense.getStatus(), createdFromParcel.getStatus());

        parcel.recycle();
    }
}
