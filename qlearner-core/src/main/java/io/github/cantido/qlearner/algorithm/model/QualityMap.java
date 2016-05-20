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

/**
 * An object that can store the {@link Quality} values of {@link State}-{@link Action} pairs.
 */
public interface QualityMap {
  /**
   * Store the specified value.
   * 
   * @param state the starting {@code State}.
   * @param action the {@code Action} taken from that {@code State}.
   * @param quality the {@code Quality} of that combination.
   */
  void put(State state, Action action, Quality quality);

  /**
   * Retrieve a value from this object.
   * 
   * @param state a starting {@code State}.
   * @param action the {@code Action} taken from that {@code State}.
   * @return the {@code Quality} of that combination, or the specified default value if the given
   *         {@code State}-{@code Action} combination has not yet been stored.
   * 
   * @see #getDefaultQuality()
   */
  Quality get(State state, Action action);

  /**
   * Get the best possible quality for the given {@code State}.
   * 
   * @param state the {@code State} to find the best {@code Quality} for
   * @return the highest {@code Quality} for any {@code Action} that can be taken from the specified
   *         {@code State}.
   */
  Quality getBestQuality(State state);

  /**
   * Get the {@code Quality} vlaue that will be returned for any {@code State}-{@code Action}
   * combination has not yet been stored.
   * 
   * @return the default {@code Quality} value.
   */
  Quality getDefaultQuality();

  /**
   * Build a {@code StateActionQuality} triplet for the specified {@code State}, {@code Action}, and
   * that combination's {@code Quality} according to this object.
   * 
   * @param state the {@code State} for the given triplet.
   * @param action the {@code Action} for the given triplet.
   * @return a triplet containing the pair and their {@code Quality}.
   */
  StateActionQuality getTriplet(State state, Action action);
}
