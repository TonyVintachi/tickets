package com.sa.traffic.infringement.models;

import android.os.Parcel;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class DriverTest {

    @Test
    public void constructor_initializesBasicFieldsCorrectly() {
        String name = "John Doe";
        String idNumber = "1234567890123";
        String contact = "0820000000";

        Driver driver = new Driver(name, idNumber, contact);

        assertEquals(name, driver.getName());
        assertEquals(idNumber, driver.getIdNumber());
        assertEquals(contact, driver.getContact());
        assertNotNull(driver.getOffenses());
        assertTrue(driver.getOffenses().isEmpty());
    }

    @Test
    public void gettersAndSetters_workCorrectlyForBasicFields() {
        Driver driver = new Driver("Initial Name", "InitialID", "InitialContact");

        String newName = "Jane Doe";
        driver.setName(newName);
        assertEquals(newName, driver.getName());

        String newId = "9876543210987";
        driver.setIdNumber(newId);
        assertEquals(newId, driver.getIdNumber());

        String newContact = "0831112222";
        driver.setContact(newContact);
        assertEquals(newContact, driver.getContact());
    }

    @Test
    public void addOffenseAndGetOffenses_workCorrectly() {
        Driver driver = new Driver("Test Driver", "TestID", "TestContact");
        Offense offense1 = new Offense("Speeding", "2024-01-01", "N1", 250.0, "Outstanding");
        Offense offense2 = new Offense("Parking", "2024-01-05", "Main St", 100.0, "Paid");

        driver.addOffense(offense1);
        assertEquals(1, driver.getOffenses().size());
        assertEquals(offense1, driver.getOffenses().get(0));

        driver.addOffense(offense2);
        assertEquals(2, driver.getOffenses().size());
        assertEquals(offense2, driver.getOffenses().get(1));

        List<Offense> newOffenses = new ArrayList<>();
        Offense offense3 = new Offense("Seatbelt", "2024-02-01", "R21", 300.0, "Outstanding");
        newOffenses.add(offense3);
        driver.setOffenses(newOffenses);
        assertEquals(1, driver.getOffenses().size());
        assertEquals(offense3, driver.getOffenses().get(0));
    }

    @Test
    public void parcelableImplementation_isCorrect() {
        Driver originalDriver = new Driver("Parcel Driver", "ParcelID123", "0812345678");
        Offense offense1 = new Offense("Test Offense 1", "2024-03-01", "Location A", 111.0, "Status X");
        Offense offense2 = new Offense("Test Offense 2", "2024-03-02", "Location B", 222.0, "Status Y");
        originalDriver.addOffense(offense1);
        originalDriver.addOffense(offense2);

        Parcel parcel = Parcel.obtain();
        originalDriver.writeToParcel(parcel, originalDriver.describeContents());
        parcel.setDataPosition(0);

        Driver createdFromParcel = Driver.CREATOR.createFromParcel(parcel);

        assertNotNull(createdFromParcel);
        assertEquals(originalDriver.getName(), createdFromParcel.getName());
        assertEquals(originalDriver.getIdNumber(), createdFromParcel.getIdNumber());
        assertEquals(originalDriver.getContact(), createdFromParcel.getContact());

        assertNotNull(createdFromParcel.getOffenses());
        assertEquals(originalDriver.getOffenses().size(), createdFromParcel.getOffenses().size());

        for (int i = 0; i < originalDriver.getOffenses().size(); i++) {
            Offense originalOffense = originalDriver.getOffenses().get(i);
            Offense parcelOffense = createdFromParcel.getOffenses().get(i);
            assertEquals(originalOffense.getType(), parcelOffense.getType());
            assertEquals(originalOffense.getDate(), parcelOffense.getDate());
            assertEquals(originalOffense.getLocation(), parcelOffense.getLocation());
            assertEquals(originalOffense.getAmount(), parcelOffense.getAmount(), 0.001);
            assertEquals(originalOffense.getStatus(), parcelOffense.getStatus());
        }

        parcel.recycle();
    }
}
