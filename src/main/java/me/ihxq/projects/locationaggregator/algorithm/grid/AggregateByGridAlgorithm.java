package me.ihxq.projects.locationaggregator.algorithm.grid;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import me.ihxq.projects.locationaggregator.algorithm.AggregateAlgorithm;
import me.ihxq.projects.locationaggregator.model.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AggregateByGridAlgorithm implements AggregateAlgorithm<SquareScope> {

    private final int horizontalPartition;
    private final int verticalPartition;

    public static AggregateByGridAlgorithm of(int horizontalPartition, int verticalPartition) {
        if (horizontalPartition <= 0 || verticalPartition <= 0) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Both horizontalPartition and verticalSIze should larger than zero, actual horizontalPartition={0}, verticalPartition={1}",
                    horizontalPartition, verticalPartition));
        }
        return new AggregateByGridAlgorithm(horizontalPartition, verticalPartition);
    }

    @Override
    public <T extends Location> Collection<AggregatedLocations<SquareScope, T>> aggregate(Collection<T> locations) {
        if (locations == null || locations.isEmpty()) {
            return Collections.emptyList();
        }

        final Stat stat = locations.stream().collect(Stat.collector());
        final Collection<Grid<T>> grids = generateGrids(stat);
        for (T location : locations) {
            boolean settled = false;
            for (Grid<T> grid : grids) {
                if (grid.tryPut(location)) {
                    settled = true;
                    break;
                }
            }
            if (!settled) {
                throw new IllegalStateException();
            }
        }
        return grids.stream()
                .map(Grid::toAggregatedLocations)
                .collect(Collectors.toList());
    }

    private <T extends Location> Collection<Grid<T>> generateGrids(Stat stat) {
        final List<Grid<T>> grids = new ArrayList<>();
        for (double x = 0; x < horizontalPartition; x++) {
            for (double y = 0; y < verticalPartition; y++) {
                final Grid<T> grid = GridBuilder.newInstance()
                        .horizontalBase(stat.getMinXAxis())
                        .verticalBase(stat.getMinYAxis())
                        .horizontalIndex(x)
                        .verticalIndex(y)
                        .horizontalPartitions(horizontalPartition)
                        .verticalPartitions(verticalPartition)
                        .horizontalLength(stat.getHorizontalLength())
                        .verticalLength(stat.getVerticalLength())
                        .build();
                grids.add(grid);
            }
        }
        return grids;
    }
}
