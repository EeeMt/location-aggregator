package me.ihxq.projects.locationaggregator.algorithm;

import me.ihxq.projects.locationaggregator.algorithm.grid.AggregateByGridAlgorithm;
import me.ihxq.projects.locationaggregator.model.AggregatedLocations;
import me.ihxq.projects.locationaggregator.model.SimpleLocation;
import me.ihxq.projects.locationaggregator.model.SquareScope;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AggregateByGridAlgorithmTest {

    @Test
    void illegalPartitionSize() {
        assertThrows(IllegalArgumentException.class, () -> AggregateByGridAlgorithm.of(2, 0));
        assertThrows(IllegalArgumentException.class, () -> AggregateByGridAlgorithm.of(0, 2));
        assertThrows(IllegalArgumentException.class, () -> AggregateByGridAlgorithm.of(0, 0));
    }

    @Test
    void twoXOne() {
        final List<SimpleLocation> locations = generateFourLocationDistributeBySquare();
        final AggregateByGridAlgorithm algorithm = AggregateByGridAlgorithm.of(2, 1);
        final Collection<AggregatedLocations<SquareScope, SimpleLocation>> result = algorithm.aggregate(locations);
        assertEquals(2, result.size());
        result.forEach(aggregatedLocation -> assertEquals(2, aggregatedLocation.getLocations().size()));
    }

    @Test
    void oneXTwo() {
        final List<SimpleLocation> locations = generateFourLocationDistributeBySquare();
        final AggregateByGridAlgorithm algorithm = AggregateByGridAlgorithm.of(1, 2);
        final Collection<AggregatedLocations<SquareScope, SimpleLocation>> result = algorithm.aggregate(locations);
        assertEquals(2, result.size());
        result.forEach(aggregatedLocation -> assertEquals(2, aggregatedLocation.getLocations().size()));
    }

    @Test
    void twoXTwo() {
        final List<SimpleLocation> locations = generateFourLocationDistributeBySquare();
        final AggregateByGridAlgorithm algorithm = AggregateByGridAlgorithm.of(2, 2);
        final Collection<AggregatedLocations<SquareScope, SimpleLocation>> result = algorithm.aggregate(locations);
        assertEquals(4, result.size());
        result.forEach(aggregatedLocation -> assertEquals(1, aggregatedLocation.getLocations().size()));
    }

    @Test
    void threeXThree() {
        final List<SimpleLocation> locations = Stream.of(
                        SimpleLocation.of(0.0d, 0.0d),
                        SimpleLocation.of(1.5d, 0.0d),
                        SimpleLocation.of(3.0d, 0.0d),
                        SimpleLocation.of(0.5d, 1.5d),
                        SimpleLocation.of(1.5d, 1.5d),
                        SimpleLocation.of(2.5d, 1.5d),
                        SimpleLocation.of(0.0d, 3.0d),
                        SimpleLocation.of(1.5d, 2.5d),
                        SimpleLocation.of(2.5d, 2.5d)
                )
                .collect(Collectors.toList());

        final AggregateByGridAlgorithm algorithm = AggregateByGridAlgorithm.of(3, 3);
        final Collection<AggregatedLocations<SquareScope, SimpleLocation>> result = algorithm.aggregate(locations);
        assertEquals(9, result.size());
        result.forEach(aggregatedLocation -> assertEquals(1, aggregatedLocation.getLocations().size()));
    }

    @Test
    void twoXThree() {
        final List<SimpleLocation> locations = Stream.of(
                        SimpleLocation.of(0.0d, 0.0d),
                        SimpleLocation.of(3.0d, 0.0d),
                        SimpleLocation.of(0.5d, 1.5d),
                        SimpleLocation.of(2.5d, 1.5d),
                        SimpleLocation.of(0.0d, 3.0d),
                        SimpleLocation.of(2.5d, 2.5d)
                )
                .collect(Collectors.toList());

        final AggregateByGridAlgorithm algorithm = AggregateByGridAlgorithm.of(2, 3);
        final Collection<AggregatedLocations<SquareScope, SimpleLocation>> result = algorithm.aggregate(locations);
        assertEquals(6, result.size());
        result.forEach(aggregatedLocation -> assertEquals(1, aggregatedLocation.getLocations().size()));
    }

    @Test
    void many() {
        int pointsNum = 1_000_000;
        final List<SimpleLocation> locations = IntStream.range(0, pointsNum)
                .boxed()
                .map(i -> {
                    final ThreadLocalRandom current = ThreadLocalRandom.current();
                    return SimpleLocation.of(current.nextDouble(0, 100), current.nextDouble(0, 100));
                })
                .collect(Collectors.toList());

        final AggregateByGridAlgorithm algorithm = AggregateByGridAlgorithm.of(3, 3);
        algorithm.aggregate(locations);
        //        Thread.sleep(1000 * 30);
        final Instant start = Instant.now();
        final Collection<AggregatedLocations<SquareScope, SimpleLocation>> result = algorithm.aggregate(locations);
        final Instant end = Instant.now();
        assertEquals(9, result.size());
        assertTrue(Duration.between(start, end).toMillis() < 100);
        final int sum = result.stream()
                .map(AggregatedLocations::getLocations)
                .mapToInt(Collection::size)
                .sum();
        assertEquals(pointsNum, sum);
    }

    private List<SimpleLocation> generateFourLocationDistributeBySquare() {
        return Stream.of(
                        SimpleLocation.of(0d, 0d),
                        SimpleLocation.of(0d, 2d),
                        SimpleLocation.of(2d, 0d),
                        SimpleLocation.of(2d, 2d)
                )
                .collect(Collectors.toList());
    }

}