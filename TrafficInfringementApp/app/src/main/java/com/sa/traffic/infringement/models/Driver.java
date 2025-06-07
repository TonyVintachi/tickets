package com.sa.traffic.infringement.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class Driver implements Parcelable {
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

    // Default constructor for use with Parcelable or if no initial data
    public Driver() {
        this.offenses = new ArrayList<>();
    }


    protected Driver(Parcel in) {
        name = in.readString();
        idNumber = in.readString();
        contact = in.readString();
        offenses = in.createTypedArrayList(Offense.CREATOR);
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(idNumber);
        dest.writeString(contact);
        dest.writeTypedList(offenses);
    }
}
