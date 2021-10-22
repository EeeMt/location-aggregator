package me.ihxq.projects.locationaggregator.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class SimpleCounterLocation implements Location {

    private double longitude;
    private double latitude;
    private int count;

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }
}
