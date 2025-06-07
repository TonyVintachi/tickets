package com.sa.traffic.infringement.models;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private String name;
    private String idNumber;
    private String contact;
    private List<Offense> offenses;

    public Driver(String name, String idNumber, String contact) {
        this.name = name;
        this.idNumber = idNumber;
        this.contact = contact;
        this.offenses = new ArrayList<>(); // Initialize with an empty list
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Offense> getOffenses() {
        return offenses;
    }

    public void setOffenses(List<Offense> offenses) {
        this.offenses = offenses;
    }

    // Optional: Method to add an offense
    public void addOffense(Offense offense) {
        if (this.offenses == null) {
            this.offenses = new ArrayList<>();
        }
        this.offenses.add(offense);
    }
}
