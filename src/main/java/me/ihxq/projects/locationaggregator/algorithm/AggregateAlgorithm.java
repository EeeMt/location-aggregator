package me.ihxq.projects.locationaggregator.algorithm;

import me.ihxq.projects.locationaggregator.model.AggregatedLocations;
import me.ihxq.projects.locationaggregator.model.AggregatedScope;
import me.ihxq.projects.locationaggregator.model.Location;

import java.util.Collection;

/**
 * @author xq.h
 * on 2020/2/16 17:35
 **/
public interface AggregateAlgorithm<S extends AggregatedScope> {

    <T extends Location> Collection<AggregatedLocations<S, T>> aggregate(Collection<T> locations);
}
