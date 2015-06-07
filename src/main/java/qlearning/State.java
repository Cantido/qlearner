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

import java.util.Set;

import qlearning.domain.Reward;

/**
 * A condition that you want to reward or discourage the system from arriving at, and all of the actions that it is
 * possible to take while in this condition.
 * <p>
 * The desirability of this condition is determined by the reward provided by {@link #getReward}. Higher values will
 * make the system prefer this state. Possible actions are provided by {@link #getActions}.
 * <p>
 * <p>
 * Note: This class <strong>must</strong> implement {@link #hashCode()} and {@link #equals(Object)}! The system uses
 * these comparisons to look up the quality values of {@code State-Action} pairs, so if two different {@code State}s are
 * seen as equivalent, then the system will not work correctly.
 * <p>
 */
public interface State {

    /**
     * Get the desirability of the current state. Higher values will make the {@link Agent} prefer this state, and lower
     * values will make it avoid this state. It is best to choose rewards based on the desirability of the outcome, not
     * of behaviors, to allow the system the most freedom to search. It robs the algorithm of its power to reward for
     * behavior and not outcome.
     * 
     * @return the score of this state's desirability
     */
    Reward getReward();

    /**
     * Get all {@link Action}s that is possible to perform while in this state
     * 
     * @return all actions that can be taken
     */
    Set<Action> getActions();
}
