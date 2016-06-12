/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with QLearner. If not,
 * see <http://www.gnu.org/licenses/>.
 */

package io.github.cantido.qlearner.agent;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.cantido.qlearner.client.Action;
import io.github.cantido.qlearner.client.State;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Signed;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * A single {@link State}-{@link Action} pair choice made by an {@link Agent} during one iteration
 * of its algorithm.
 * <p>
 * The {@code State} represents the state that the agent was in when it started an iteration, and
 * the {@code Action} represents the action that was chosen to leave that state.
 * </p>
 */
@Immutable
@ThreadSafe
public class Step {
  @Nonnull
  private final State startingState;
  @Nonnull
  private final Action leavingAction;

  /**
   * Create a {@code Step} containing the given {@code State} and {@code Action}.
   * 
   * @param startingState the {@code State} that the agent originated from.
   * @param leavingAction the {@code Action} that was taken from that state.
   */
  public Step(State startingState, Action leavingAction) {
    this.startingState = startingState;
    this.leavingAction = leavingAction;
  }

  /**
   * Get the action that was taken to leave this step's {@link State}.
   * 
   * @return the {@code Action} that was taken to leave this {@code Step}'s {@code State}.
   */
  public Action getLeavingAction() {
    return leavingAction;
  }

  /**
   * Get the {@code State} that preceded this step's {@link Action}.
   * 
   * @return the starting {@code State}.
   */
  public State getStartingState() {
    return startingState;
  }

  @SuppressFBWarnings(value = "NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION",
      justification = "We are overriding equals, which is defined "
          + "as @Nullable. This is a false positive.")
  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Step step = (Step) other;
    return Objects.equals(startingState, step.startingState)
        && Objects.equals(leavingAction, step.leavingAction);
  }

  @Override
  @Signed
  public int hashCode() {
    return Objects.hash(startingState, leavingAction);
  }
}
