package me.ihxq.projects.locationaggregator.algorithm.grid;

import lombok.Getter;
import me.ihxq.projects.locationaggregator.model.Location;

import java.util.Comparator;
import java.util.stream.Collector;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.util.stream.Collector.Characteristics.UNORDERED;

@Getter
class Stat {
    private int count;
    private double
            minXAxis = NEGATIVE_INFINITY,
            maxXAxis = POSITIVE_INFINITY,
            minYAxis = NEGATIVE_INFINITY,
            maxYAxis = POSITIVE_INFINITY,
            horizontalLength,
            verticalLength;
    private final Comparator<Double> comparator = Comparator.naturalOrder();


    void accept(Location val) {
        if (count == 0) {
            minXAxis = maxXAxis = val.getLongitude();
            minYAxis = maxYAxis = val.getLatitude();
            count++;
            return;
        }
        if (comparator.compare(val.getLongitude(), minXAxis) < 0) {
            minXAxis = val.getLongitude();
        } else if (comparator.compare(val.getLongitude(), maxXAxis) > 0) {
            maxXAxis = val.getLongitude();
        }

        if (comparator.compare(val.getLatitude(), minYAxis) < 0) {
            minYAxis = val.getLatitude();
        } else if (comparator.compare(val.getLatitude(), maxYAxis) > 0) {
            maxYAxis = val.getLatitude();
        }

        count++;
    }

    Stat combine(Stat that) {
        if (this.count == 0) return that;
        if (that.count == 0) return this;

        this.count += that.count;
        if (comparator.compare(that.minXAxis, this.minXAxis) < 0) {
            this.minXAxis = that.minXAxis;
        }
        if (comparator.compare(that.maxXAxis, this.maxXAxis) > 0) {
            this.maxXAxis = that.maxXAxis;
        }

        if (comparator.compare(that.minYAxis, this.minYAxis) < 0) {
            this.minYAxis = that.minYAxis;
        }
        if (comparator.compare(that.maxYAxis, this.maxYAxis) > 0) {
            this.maxYAxis = that.maxYAxis;
        }

        return this;
    }

    static Stat finish(Stat stat) {
        stat.horizontalLength = stat.maxXAxis - stat.minXAxis;
        stat.verticalLength = stat.maxYAxis - stat.minYAxis;
        return stat;
    }


    public static Collector<Location, Stat, Stat> collector() {
        return Collector.of(
                Stat::new,
                Stat::accept,
                Stat::combine,
                Stat::finish,
                UNORDERED
        );
    }

}
