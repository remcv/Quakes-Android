package com.example.quakes;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EarthquakeObservation
{
    // fields
    private String place;
    private double magnitude;
    private LocalDateTime date;

    // constructor
    public EarthquakeObservation(String place, double magnitude, LocalDateTime date)
    {
        this.place = place;
        this.magnitude = magnitude;
        this.date = date;
    }

    public EarthquakeObservation()
    {
        // empty
    }

    // getters
    public String getPlace()
    {
        return place;
    }

    public double getMagnitude()
    {
        return magnitude;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    // override toString()
    @NonNull
    @Override
    public String toString()
    {
        String info = null;

        info = place + " " + magnitude;

        return info;
    }
}
