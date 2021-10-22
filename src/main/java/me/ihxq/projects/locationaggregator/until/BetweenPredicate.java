package me.ihxq.projects.locationaggregator.until;

import lombok.AllArgsConstructor;

import java.util.function.DoublePredicate;

/**
 *
 */
@AllArgsConstructor(staticName = "of")
public class BetweenPredicate implements DoublePredicate {
    private double lowerLimit;
    private double upperLimit;
    private CloseMode closeMode;

    public boolean test(double target) {
        switch (closeMode) {
            case NONE:
                return target > lowerLimit && target < upperLimit;
            case LOWER_LIMIT_CLOSED:
                return target >= lowerLimit && target < upperLimit;
            case UPPER_LIMIT_CLOSED:
                return target > lowerLimit && target <= upperLimit;
            case BOTH_LIMIT_CLOSED:
                return target >= lowerLimit && target <= upperLimit;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public enum CloseMode {
        NONE, LOWER_LIMIT_CLOSED, UPPER_LIMIT_CLOSED, BOTH_LIMIT_CLOSED
    }
}
