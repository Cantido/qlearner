package qlearning.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import qlearning.Action;
import qlearning.QualityMap;
import qlearning.State;
import qlearning.domain.Quality;

public class QualityHashMap implements QualityMap {
    private Map<ImmutablePair<State, Action>, Quality> qualities = new HashMap<>();
    
    private Quality defaultQuality = new Quality(NumberUtils.DOUBLE_ZERO);
    
    public void setDefaultQuality(Quality defaultQuality) {
        this.defaultQuality = defaultQuality;
    }
    
    public Quality getDefaultQuality() {
        return this.defaultQuality;
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
            qualityToGet = defaultQuality;

        } else if (qualities.containsKey(pair)) {
            qualityToGet = qualities.get(pair);

        } else {
            qualityToGet = defaultQuality;
        }

        return qualityToGet;
    }
    
    @Override
    public Quality getBestQuality(State state, Set<Action> actions) {
        TreeSet<Quality> qualities = new TreeSet<Quality>();
        
        for (Action action : actions) {
            qualities.add(get(state, action));
        }
        return qualities.last();
    }
}
