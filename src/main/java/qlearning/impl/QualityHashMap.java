package qlearning.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import qlearning.Action;
import qlearning.QualityMap;
import qlearning.State;

public class QualityHashMap implements QualityMap {
    
    private Map<ImmutablePair<State, Action>, Double> qualities = new HashMap<>();
    
    private double defaultQuality = NumberUtils.DOUBLE_ZERO;
    
    public void setDefaultQuality(double defaultQuality) {
        this.defaultQuality = defaultQuality;
    }
    
    public double getDefaultQuality() {
        return this.defaultQuality;
    }
    
    @Override
    public void put(State state, Action action, double quality) {
        qualities.put(ImmutablePair.of(state, action), quality);
    }

    @Override
    public double get(State state, Action action) {
        double qualityToGet;
        ImmutablePair<State, Action> pair = ImmutablePair.of(state, action);

        if (state == null || action == null) {
            qualityToGet = defaultQuality;

        } else if (qualities.containsKey(pair)) {
            qualityToGet = qualities.get(pair);

        } else {
            qualityToGet = defaultQuality;
        }

        return qualityToGet;
    }

}
