package com.sa.traffic.infringement.models;

import android.os.Parcel;
import org.junit.Test;
import static org.junit.Assert.*;

public class VehicleTest {

    @Test
    public void constructor_initializesFieldsCorrectly() {
        String make = "Toyota";
        String model = "Corolla";
        String regNum = "CA123456";
        String owner = "John Doe";
        String status = "Licensed";

        Vehicle vehicle = new Vehicle(make, model, regNum, owner, status);

        assertEquals(make, vehicle.getMake());
        assertEquals(model, vehicle.getModel());
        assertEquals(regNum, vehicle.getRegistrationNumber());
        assertEquals(owner, vehicle.getOwnerName());
        assertEquals(status, vehicle.getStatus());
    }

    @Test
    public void gettersAndSetters_workCorrectly() {
        Vehicle vehicle = new Vehicle("InitialMake", "InitialModel", "InitialReg", "InitialOwner", "InitialStatus");

        String newMake = "Honda";
        vehicle.setMake(newMake);
        assertEquals(newMake, vehicle.getMake());

        String newModel = "Civic";
        vehicle.setModel(newModel);
        assertEquals(newModel, vehicle.getModel());

        String newReg = "GP987654";
        vehicle.setRegistrationNumber(newReg);
        assertEquals(newReg, vehicle.getRegistrationNumber());

        String newOwner = "Jane Smith";
        vehicle.setOwnerName(newOwner);
        assertEquals(newOwner, vehicle.getOwnerName());

        String newStatus = "Unlicensed";
        vehicle.setStatus(newStatus);
        assertEquals(newStatus, vehicle.getStatus());
    }

    @Test
    public void parcelableImplementation_isCorrect() {
        Vehicle originalVehicle = new Vehicle("Ford", "Ranger", "CFD555123", "Mike Brown", "Expired");

        Parcel parcel = Parcel.obtain();
        originalVehicle.writeToParcel(parcel, originalVehicle.describeContents());
        parcel.setDataPosition(0);

        Vehicle createdFromParcel = Vehicle.CREATOR.createFromParcel(parcel);

        assertNotNull(createdFromParcel);
        assertEquals(originalVehicle.getMake(), createdFromParcel.getMake());
        assertEquals(originalVehicle.getModel(), createdFromParcel.getModel());
        assertEquals(originalVehicle.getRegistrationNumber(), createdFromParcel.getRegistrationNumber());
        assertEquals(originalVehicle.getOwnerName(), createdFromParcel.getOwnerName());
        assertEquals(originalVehicle.getStatus(), createdFromParcel.getStatus());

        parcel.recycle();
    }
}
