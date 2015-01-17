package qlearning;

/**
 * An act that can be performed that will lead to a change in the {@link Environment}'s current {@link State}.
 * <p>
 * The {@link Agent} assumes that taking actions using {@link #execute()} somehow changes the state of the
 * {@link Environment}. Calling {@code #execute()} on {@code Action}s is the "output" of this system. This could drive
 * motors, change an HVAC's set temperature, move a game piece, and so on. As long as this has a chance of changing what
 * we get from {@link Environment#getState()}, then the system can learn to optimize its choices.
 * </p>
 * <p>
 * Note: This class <strong>must</strong> implement {@link #hashCode()} and {@link #equals(Object)}! The system uses
 * these comparisons to look up the quality values of {@code State-Action} pairs, so if two different {@code Action}s
 * are seen as equivalent, then the system will not work correctly. For example, if you are moving a game piece on a
 * board, make sure that "up" means the same "up" everywhere!
 * <p>
 */
public interface Action {
    /**
     * Execute the given action.
     */
    void execute();
}
