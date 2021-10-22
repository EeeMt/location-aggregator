package me.ihxq.projects.locationaggregator.algorithm.grid;

import lombok.AllArgsConstructor;
import me.ihxq.projects.locationaggregator.model.AggregatedLocations;
import me.ihxq.projects.locationaggregator.model.Location;
import me.ihxq.projects.locationaggregator.model.SquareScope;
import me.ihxq.projects.locationaggregator.until.BetweenPredicate;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
class Grid<T extends Location> {
    private final double topEdge;
    private final double bottomEdge;
    private final double leftEdge;
    private final double rightEdge;
    private final BetweenPredicate horizontalWithinPredicate;
    private final BetweenPredicate verticalWithinPredicate;

    private final Collection<T> locations = new ArrayList<>();

    public boolean tryPut(T location) {
        final boolean isWithin = horizontalWithinPredicate.test(location.getLongitude())
                && verticalWithinPredicate.test(location.getLatitude());
        if (isWithin) {
            locations.add(location);
        }
        return isWithin;
    }

    public AggregatedLocations<SquareScope, T> toAggregatedLocations() {
        final AggregatedLocations<SquareScope, T> aggregatedLocations = new AggregatedLocations<>();
        aggregatedLocations.setScope(SquareScope.of(topEdge, bottomEdge, leftEdge, rightEdge));
        aggregatedLocations.setLocations(locations);
        return aggregatedLocations;
    }

}
