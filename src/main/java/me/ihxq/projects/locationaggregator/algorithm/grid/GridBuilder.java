package me.ihxq.projects.locationaggregator.algorithm.grid;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.ihxq.projects.locationaggregator.model.Location;
import me.ihxq.projects.locationaggregator.until.BetweenPredicate;

import static me.ihxq.projects.locationaggregator.until.BetweenPredicate.CloseMode.BOTH_LIMIT_CLOSED;
import static me.ihxq.projects.locationaggregator.until.BetweenPredicate.CloseMode.LOWER_LIMIT_CLOSED;

@Setter
@Accessors(fluent = true)
@NoArgsConstructor(staticName = "newInstance")
public class GridBuilder {
    private double horizontalIndex, verticalIndex;
    private double horizontalPartitions, verticalPartitions;
    private double horizontalBase, verticalBase;
    private double horizontalLength, verticalLength;

    public <T extends Location> Grid<T> build() {
        double leftEdge = horizontalBase + ((horizontalIndex / horizontalPartitions) * horizontalLength);
        double rightEdge = horizontalBase + (((horizontalIndex + 1.0) / horizontalPartitions) * horizontalLength);
        double bottomEdge = verticalBase + (verticalIndex / verticalPartitions) * verticalLength;
        double topEdge = verticalBase + (((verticalIndex + 1.0) / verticalPartitions) * verticalLength);
        BetweenPredicate horizontalWithinPredicate;
        if (horizontalIndex == horizontalPartitions - 1) {
            horizontalWithinPredicate = BetweenPredicate.of(leftEdge, rightEdge, BOTH_LIMIT_CLOSED);
        } else {
            horizontalWithinPredicate = BetweenPredicate.of(leftEdge, rightEdge, LOWER_LIMIT_CLOSED);
        }

        BetweenPredicate verticalWithinPredicate;
        if (verticalIndex == verticalPartitions - 1) {
            verticalWithinPredicate = BetweenPredicate.of(bottomEdge, topEdge, BOTH_LIMIT_CLOSED);
        } else {
            verticalWithinPredicate = BetweenPredicate.of(bottomEdge, topEdge, LOWER_LIMIT_CLOSED);
        }
        return new Grid<>(topEdge, bottomEdge, leftEdge, rightEdge, horizontalWithinPredicate, verticalWithinPredicate);
    }
}
