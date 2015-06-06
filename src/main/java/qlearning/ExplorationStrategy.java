package qlearning;

import java.util.Collection;

import qlearning.domain.Quality;
import qlearning.domain.StateActionQuality;

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
    public Action getNextAction(Collection<StateActionQuality> stateActionQualities);
}
