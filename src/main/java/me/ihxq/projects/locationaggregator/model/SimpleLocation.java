package me.ihxq.projects.locationaggregator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class SimpleLocation implements Location {

    private double longitude;
    private double latitude;

}
