package com.sa.traffic.infringement.models;

public class Vehicle {
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
}
