package me.ihxq.projects.locationaggregator.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class SquareScope implements AggregatedScope {
    private final double topEdge;
    private final double bottomEdge;
    private final double leftEdge;
    private final double rightEdge;

    public SimpleLocation toLocation() {
        return SimpleLocation.of((rightEdge - leftEdge) / 2, (topEdge - bottomEdge) / 2);
    }
}
