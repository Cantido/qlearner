/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QLearner.  If not, see <http://www.gnu.org/licenses/>.
 */

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
