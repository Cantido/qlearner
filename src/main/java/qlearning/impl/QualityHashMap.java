package qlearning.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import qlearning.Action;
import qlearning.QualityMap;
import qlearning.State;
import qlearning.domain.Quality;

public class QualityHashMap implements QualityMap {
    
    private Map<ImmutablePair<State, Action>, Quality> qualities = new HashMap<>();
    
    private double defaultQualityValue = NumberUtils.DOUBLE_ZERO;
    
    public void setDefaultQuality(double defaultQuality) {
        this.defaultQualityValue = defaultQuality;
    }
    
    public double getDefaultQuality() {
        return this.defaultQualityValue;
    }
    
    @Override
    public void put(State state, Action action, Quality quality) {
        qualities.put(ImmutablePair.of(state, action), quality);
    }

    @Override
    public Quality get(State state, Action action) {
        Quality qualityToGet;
        ImmutablePair<State, Action> pair = ImmutablePair.of(state, action);

        if (state == null || action == null) {
            qualityToGet = new Quality(defaultQualityValue);

        } else if (qualities.containsKey(pair)) {
            qualityToGet = qualities.get(pair);

        } else {
            qualityToGet = new Quality(defaultQualityValue);
        }

        return qualityToGet;
    }

}
