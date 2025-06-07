package com.sa.traffic.infringement.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Vehicle implements Parcelable {
    private String make;
    private String model;
    private String registrationNumber;
    private String ownerName;
    private String status; // e.g. "Licensed", "Unlicensed", "Stolen"

    public Vehicle(String make, String model, String registrationNumber, String ownerName, String status) {
        this.make = make;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.ownerName = ownerName;
        this.status = status;
    }

    // Default constructor
    public Vehicle() {
    }

    protected Vehicle(Parcel in) {
        make = in.readString();
        model = in.readString();
        registrationNumber = in.readString();
        ownerName = in.readString();
        status = in.readString();
    }

    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(make);
        dest.writeString(model);
        dest.writeString(registrationNumber);
        dest.writeString(ownerName);
        dest.writeString(status);
    }
}
