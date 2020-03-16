package com.example.quakes;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class EarthquakeObservation
{
    // fields
    private String city;
    private double distance;
    private double magnitude;
    private int resMagnitude;
    private LocalDate date;

    // constructor
    public EarthquakeObservation(String city, double distance, double magnitude, LocalDate date, int resMagnitude)
    {
        this.city = city;
        this.distance = distance;
        this.magnitude = magnitude;
        this.date = date;
        this.resMagnitude = resMagnitude;
    }

    public EarthquakeObservation()
    {
        // empty
    }

    // getters
    public String getCity()
    {
        return city;
    }

    public double getDistance()
    {
        return distance;
    }

    public double getMagnitude()
    {
        return magnitude;
    }

    public int getResMagnitude()
    {
        return resMagnitude;
    }

    public LocalDate getDate()
    {
        return date;
    }

    // override toString()
    @NonNull
    @Override
    public String toString()
    {
        String info = null;

        info = city + " " + magnitude + " " + distance;

        return info;
    }
}
