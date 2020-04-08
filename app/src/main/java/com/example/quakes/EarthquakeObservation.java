package com.example.quakes;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class EarthquakeObservation
{
    // fields
    private String place;
    private double magnitude;
    private LocalDateTime date;
    private final String url;

    // constructor
    public EarthquakeObservation(String place, double magnitude, LocalDateTime date, String url)
    {
        this.place = place;
        this.magnitude = magnitude;
        this.date = date;
        this.url = url;
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

    public String getUrl()
    {
        return url;
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
