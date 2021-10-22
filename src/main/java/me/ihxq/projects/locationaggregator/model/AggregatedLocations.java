package me.ihxq.projects.locationaggregator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedLocations<S extends AggregatedScope, T extends Location> {
    private S scope;
    private Collection<T> locations;
}
