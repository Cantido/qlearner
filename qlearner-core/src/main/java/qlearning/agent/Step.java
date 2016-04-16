package qlearning.agent;

import qlearning.client.Action;
import qlearning.client.State;

import java.util.Objects;

/**
 * A single choice cycle made by an {@link Agent}.
 */
public class Step {
    public final State startingState;
    public final Action leavingAction;

    public Step(State startingState, Action leavingAction) {
        this.startingState = startingState;
        this.leavingAction = leavingAction;
    }

    public Action getLeavingAction() {
        return leavingAction;
    }

    public State getStartingState() {
        return startingState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return Objects.equals(startingState, step.startingState) &&
                Objects.equals(leavingAction, step.leavingAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startingState, leavingAction);
    }
}
