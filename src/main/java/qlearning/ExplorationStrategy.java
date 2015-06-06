package qlearning;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import qlearning.domain.Quality;

/**
 * The algorithm that is used to determine the next {@link Action} to take.
 */
public interface ExplorationStrategy {
    /**
     * Pick the next {@link Action} to take
     * 
     * @param stateActionQualities {@link State}-{@link Action} pairs and their associated {@link Quality Qualities}
     * @return the chosen action
     */
    public Action getNextAction(Map<Pair<State, Action>, Quality> stateActionQualities);
}
