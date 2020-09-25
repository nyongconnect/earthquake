package com.devthrust.earthquakepro;

public class Earthquakes {
    private String place;
    double magnitude;
    long date;

    public Earthquakes(double magnitude, String place, long timeInMiliSeconds){
        this.magnitude = magnitude;
        this.place= place;
        this.date = date;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public long getDate() {
        return date;
    }
}
