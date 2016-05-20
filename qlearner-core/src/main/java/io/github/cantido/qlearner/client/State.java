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

package io.github.cantido.qlearner.client;

import io.github.cantido.qlearner.agent.Agent;
import io.github.cantido.qlearner.algorithm.model.Reward;

import java.util.Set;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * A condition that you want to reward or discourage the system from arriving at.
 * <p>
 * A {@code State} has possible {@link Action}s that can be taken that are expected to have some
 * impact on your {@link Environment}. These {@code Action}s are provided by this class's 
 * {@link #getActions} method. The desirability of this {@code State} is determined by the
 * {@link Reward} value provided by {@link #getReward}.
 * </p>
 * <p>
 * <strong>Warning</strong>: This class <em>must</em> implement {@link #hashCode()} and
 * {@link #equals(Object)}!
 * The system uses these comparisons to look up the quality values of {@code State-Action} pairs, so
 * if two different {@code State}s are seen as equivalent, then the system will not work correctly.
 * For that reason, {@code State} objects must be immutable.
 * </p>
 */
@Immutable
@ThreadSafe
public abstract class State {
  /**
   * Get the desirability of the current state. Higher values will make the {@link Agent} prefer
   * this state, and lower values will make it avoid this state. It is best to choose rewards based
   * on the desirability of the outcome, not of behaviors, to allow the system the most freedom to
   * search. It robs the algorithm of its power to reward for behavior and not outcome.
   * 
   * @return the score of this state's desirability
   */
  public abstract Reward getReward();

  /**
   * Get all {@link Action}s that is possible to perform while in this state.
   * 
   * @return all gridworld.actions that can be taken
   */
  public abstract Set<Action> getActions();
}
