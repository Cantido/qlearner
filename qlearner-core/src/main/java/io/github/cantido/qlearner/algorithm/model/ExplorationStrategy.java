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

package io.github.cantido.qlearner.algorithm.model;

import io.github.cantido.qlearner.algorithm.quality.StateActionQuality;
import io.github.cantido.qlearner.client.Action;
import io.github.cantido.qlearner.client.State;

import java.util.SortedSet;

/**
 * The algorithm that is used to determine the next {@link Action} to take.
 */
public interface ExplorationStrategy {
  /**
   * Pick the next {@link Action} to take.
   * 
   * @param stateActionQualities {@link State}-{@link Action} pairs and their associated
   *        {@link Quality Qualities}
   * @return the chosen action
   */
  Action getNextAction(SortedSet<StateActionQuality> stateActionQualities);
}
