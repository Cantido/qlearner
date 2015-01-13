package qlearning;

import java.util.Set;

/**
 * The current condition of the {@link Environment}.
 */
public interface State {
	
	/**
	 * Get the desirability of the current state. Higher values will make
	 * the {@link Agent} prefer this state, and lower values will make it
	 * avoid this state.
	 * 
	 * It is best to choose rewards based on the desirability of the outcome,
	 * not of behaviors, to allow the system the most freedom to search.
	 * Rewarding behavior, and not outcomes, robs the algorithm of its power.
	 * 
	 * @return the score of this state's desirability
	 */
	double getReward();
	
	/**
	 * Get all {@link Action}s that is possible to perform while in this state
	 * 
	 * @return all actions that can be taken
	 */
	Set<Action> getActions();
}
