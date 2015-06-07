/**
 * This file is part of qlearner
 *
 *  Qlearner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Qlearner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Qlearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning;

/**
 * The space in which {@link Action}s can be performed.
 * <p>
 * This library assumes that taking actions using {@link Action#execute()} somehow changes the state of the environment.
 * This relationship could be implemented in code, as in our Gridworld example, or in meatspace, as in a learning robot
 * or smart home thermostat system. In other words, the {@code Environment} encapsulates the input to this system, while
 * the Actions encapsulate the output.
 */
public interface Environment {
    /**
     * Get the current {@link State} of the environment.
     * 
     * @see State
     * 
     * @return the environment's current state
     */
	public State getState();
}
