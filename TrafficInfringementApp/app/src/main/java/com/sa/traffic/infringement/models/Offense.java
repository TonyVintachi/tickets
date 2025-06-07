package com.sa.traffic.infringement.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Offense implements Parcelable {
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

    protected Offense(Parcel in) {
        type = in.readString();
        date = in.readString();
        location = in.readString();
        amount = in.readDouble();
        status = in.readString();
    }

    public static final Creator<Offense> CREATOR = new Creator<Offense>() {
        @Override
        public Offense createFromParcel(Parcel in) {
            return new Offense(in);
        }

        @Override
        public Offense[] newArray(int size) {
            return new Offense[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(date);
        dest.writeString(location);
        dest.writeDouble(amount);
        dest.writeString(status);
    }
}
