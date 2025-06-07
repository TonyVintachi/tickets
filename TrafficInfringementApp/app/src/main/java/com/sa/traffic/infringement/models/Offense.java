package com.sa.traffic.infringement.models;

public class Offense {
    private String type;
    private String date;
    private String location;
    private double amount;
    private String status; // e.g., "Outstanding", "Paid"

    public Offense(String type, String date, String location, double amount, String status) {
        this.type = type;
        this.date = date;
        this.location = location;
        this.amount = amount;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
