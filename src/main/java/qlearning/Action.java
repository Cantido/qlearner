package qlearning;

/**
 * An act that can be performed that will lead to a change in the
 * {@link Environment}'s current {@link State}.
 */
public interface Action {
	void execute();
}
