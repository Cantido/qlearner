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

package qlearning.client;

/**
 * The space in which {@link Action}s can be performed.
 * <p>
 * This library assumes that taking gridworld.actions using {@link Action#run()} somehow changes the
 * state of the environment. This relationship could be implemented in code, as in our Gridworld
 * example, or in meat-space, as in a learning robot or smart home thermostat system. In other
 * words, the {@code Environment} encapsulates the input to this system, while the Actions
 * encapsulate the output.
 * </p>
 */
public interface Environment {
  /**
   * Get the current {@link State} of the environment.
   * 
   * @return the environment's current state
   */
  State getState();
}
