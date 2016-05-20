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

package io.github.cantido.qlearner.algorithm.quality;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.cantido.qlearner.algorithm.model.Quality;
import io.github.cantido.qlearner.client.Action;
import io.github.cantido.qlearner.client.State;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Signed;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * A triplet of a {@link State}, {@link Action}, and {@link Quality} of that pair.
 */
@Immutable
@ThreadSafe
public class StateActionQuality implements Comparable<StateActionQuality> {
  @Nonnull
  private final State state;
  @Nonnull
  private final Action action;
  @Nonnull
  private final Quality quality;

  /**
   * Create a {@code StateActionQuality} triplet with the given {@code Quality} value.
   * 
   * @param state the state to store in this triplet
   * @param action the action to store in this triplet
   * @param quality the quality of the state-action pair to store in this triplet
   */
  public StateActionQuality(State state, Action action, Quality quality) {
    this.state = state;
    this.action = action;
    this.quality = quality;
  }

  /**
   * Get the state stored in this triplet.
   * 
   * @return the state of this triplet.
   */
  public State getState() {
    return this.state;
  }

  /**
   * Get the action stored in this triplet.
   * 
   * @return the action of this triplet.
   */
  public Action getAction() {
    return this.action;
  }

  /**
   * Get the quality stored in this triplet.
   * 
   * @return the quality of this triplet.
   */
  public Quality getQuality() {
    return this.quality;
  }

  @Override
  public String toString() {
    return "StateActionQuality [state=" + state + ", action=" + action + ", quality=" + quality
        + "]";
  }

  @Override
  @Signed
  public int hashCode() {
    return Objects.hash(action, quality, state);
  }

  @SuppressFBWarnings(value = "NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION",
      justification = "We are overriding equals, which is defined "
          + "as @Nullable. This is a false positive.")
  @Override
  public boolean equals(@Nullable Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    
    StateActionQuality other = (StateActionQuality) obj;

    if (!action.equals(other.action)) {
      return false;
    }
    if (!quality.equals(other.quality)) {
      return false;
    }
    return state.equals(other.state);
  }

  @SuppressFBWarnings(
      value = {"NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE",
          "WEM_WEAK_EXCEPTION_MESSAGING"},
      justification = "We are overriding compareTo, which is defined "
          + "as @Nullable. This is a false positive. "
          + "There is also no possible context to add to" + "the null-check exception message.")
  @Override
  @Signed
  public int compareTo(@Nullable StateActionQuality other) {
    if (other == null) {
      throw new NullPointerException("Tried to compare this value to null.");
    }
    return quality.compareTo(other.quality);
  }
}
