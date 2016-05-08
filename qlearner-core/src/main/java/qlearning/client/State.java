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

package qlearning.client;

import java.util.Set;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import qlearning.agent.Agent;
import qlearning.domain.learning.Reward;

/**
 * A condition that you want to reward or discourage the system from arriving at, and all of the gridworld.actions that it is
 * possible to take while in this condition.
 * <p>
 * The desirability of this condition is determined by the reward provided by {@link #getReward}. Higher values will
 * make the system prefer this state. Possible gridworld.actions are provided by {@link #getActions}.
 * </p><p>
 * Note: This class <strong>must</strong> implement {@link #hashCode()} and {@link #equals(Object)}! The system uses
 * these comparisons to look up the quality values of {@code State-Action} pairs, so if two different {@code State}s are
 * seen as equivalent, then the system will not work correctly.
 * </p>
 */
public abstract class State {
    /**
     * Get the desirability of the current state. Higher values will make the {@link Agent} prefer this state, and lower
     * values will make it avoid this state. It is best to choose rewards based on the desirability of the outcome, not
     * of behaviors, to allow the system the most freedom to search. It robs the algorithm of its power to reward for
     * behavior and not outcome.
     * 
     * @return the score of this state's desirability
     */
    public abstract Reward getReward();

    /**
     * Get all {@link Action}s that is possible to perform while in this state
     * 
     * @return all gridworld.actions that can be taken
     */
    public abstract Set<Action> getActions();
    
    @Override
    public abstract int hashCode();
    
    @SuppressFBWarnings(
    		value = "NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION",
    		justification = "We are overriding equals, which is defined " +
    						"as @Nullable. This is a false positive.")
    @Override
    public abstract boolean equals(@Nullable Object other);
}
