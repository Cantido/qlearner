package qlearning;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import qlearning.domain.Quality;


public interface ExplorationStrategy {
    public Action getNextAction(Map<Pair<State, Action>, Quality> stateActionQualities);
}
