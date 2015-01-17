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
