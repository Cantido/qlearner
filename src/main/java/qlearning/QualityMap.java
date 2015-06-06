package qlearning;

import java.util.Set;

import qlearning.domain.Quality;

public interface QualityMap {
    public static final Quality MIN_QUALITY = new Quality(Double.NEGATIVE_INFINITY);
    
    public void put(State state, Action action, Quality quality);
    public Quality get(State state, Action action);
    public Quality getBestQuality(State state, Set<Action> actions);
}
