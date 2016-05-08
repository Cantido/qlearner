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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * A single choice cycle made by an {@link Agent}.
 */
@Immutable
@ThreadSafe
public class Step {
    @Nonnull private final State startingState;
    @Nonnull private final Action leavingAction;

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
    
    @SuppressFBWarnings(
    		value = "NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION",
    		justification = "We are overriding equals, which is defined " +
    						"as @Nullable. This is a false positive.")
    @Override
    public boolean equals(@Nullable Object o) {
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
