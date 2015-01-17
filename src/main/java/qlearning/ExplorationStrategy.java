package qlearning;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;


public interface ExplorationStrategy {
    public Action getNextAction(Map<Pair<State, Action>, Double> stateActionQualities);
}
