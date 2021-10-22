package me.ihxq.projects.locationaggregator;

import me.ihxq.projects.locationaggregator.model.Location;
import me.ihxq.projects.locationaggregator.model.SimpleLocation;

import java.util.Collection;

public class LocationAggregator {

    public static <T extends Location> Collection<SimpleLocation> aggregate(Collection<T> original) {
        return null;
    }

    public static <T extends Location, R extends Location> Collection<R> aggregate() {
        return null;
    }
}
