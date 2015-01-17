package qlearning;

import static org.apache.commons.lang3.math.NumberUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performs {@link Action}s that will lead to changes in the {@link Environment}'s current {@link State}.
 * <p>
 * This is the object that will navigate and learn the problem space (the {@code Environment}). It implements the
 * Q-learning algorithm to pick the optimal {@code Action} to take while in each {@code State}.
 */
public class Agent {
    private static final double DEFAULT_STATE_ACTION_QUALITY = DOUBLE_ZERO;

    private double discountFactor = DOUBLE_ONE;
    private double explorationFactor = 0.1;
    private double learningRate = DOUBLE_ONE;

    private Environment environment;

    private Map<ImmutablePair<State, Action>, Double> qualities = new HashMap<>();

    private State previousState;
    private Action previousAction;

    private State currentState;

    Logger logger = LoggerFactory.getLogger("qlearner.Agent");

    /**
     * Set the environment, which represents the problem space in which the agent acts.
     * 
     * @see Environment
     * @param env the environment to set. Cannot be null.
     */
    public void setEnvironment(Environment env) {
        Validate.notNull(env, "Environment cannot be null");
        this.environment = env;
    }

    /**
     * Set the learning rate, which determines to what extent the newly acquired information will override the old
     * information.
     * <p>
     * A factor of 0 will make the agent not learn anything, while a factor of 1 would make the agent consider only the
     * most recent information. In fully deterministic environments, a learning rate of 1 is optimal. When the problem
     * is stochastic, the algorithm still converges under some technical conditions on the learning rate, that require
     * it to decrease to zero.
     * </p>
     * <p>
     * The default learning rate is 1.
     * </p>
     * 
     * @param lr the new learning rate to set, in the range [0, 1]
     */
    public void setLearningRate(double lr) {
        Validate.inclusiveBetween(DOUBLE_ZERO, DOUBLE_ONE, lr, "Learning rate must be between zero and one (inclusive)");
        this.learningRate = lr;
    }

    /**
     * Set the exploration factor, which determines how often the agent will choose random actions over desirable ones
     * in order to explore the problem space.
     * <p>
     * A factor of 0 will make the agent never choose random actions. A factor of 1 will make the agent always choose
     * random actions. The agent will still update quality values correctly based on the resulting state's reward, so
     * setting the exploration factor to 0 would allow you to perform a random search through the problem space.
     * </p>
     * 
     * @param ef the new exploration factor to set, in the range [0, 1]
     */
    public void setExplorationFactor(double ef) {
        Validate.inclusiveBetween(DOUBLE_ZERO, DOUBLE_ONE, ef,
                "Exploration factor must be between zero and one (inclusive)");
        this.explorationFactor = ef;
    }

    /**
     * The discount factor determines the importance of future rewards.
     * <p>
     * A factor of 0 will make the agent "myopic" (or short-sighted) by only considering current rewards, while a factor
     * approaching 1 will make it strive for a long-term high reward. If the discount factor meets or exceeds 1, the
     * quality values may diverge.
     * </p>
     * 
     * @param df the new discount factor to set, in the range [0, &infin;)
     */
    public void setDiscountFactor(double df) {
        Validate.inclusiveBetween(DOUBLE_ZERO, Double.POSITIVE_INFINITY, df,
                "Discount factor must be greater than or equal to zero");
        this.discountFactor = df;
    }

    /**
     * Reset memory of the previous and current states, so we can "pick up the piece and put it elsewhere."
     * <p>
     * Call this method when there will be a change in the current {@code State} that you don't want this {@code Agent}
     * learning from. In some systems (like our {@link qlearning.example.gridworld} example), you must call this method
     * when you have reached the goal state and wish to move back to the start state without messing up your hard-earned
     * Quality values.
     * </p>
     */
    public void resetState() {
        this.previousState = null;
        this.previousAction = null;
        this.currentState = null;
    }

    /**
     * Determine the next {@link Action} to take, and then call its {@link Action#execute()} method.
     * <p>
     * This {@code Agent} will also update the Quality value of the previous {@link State} and the {@code Action} that
     * got us from there to here, based on the current {@code State}'s reward value.
     * </p>
     */
    public void takeNextAction() {
        logger.debug("---- NEW TICK --- entered takeNextAction");

        assertSystemCondition(this.environment != null, "Cannot take an action without an environment.");

        currentState = environment.getState();

        logger.debug("Got current state: {}", currentState);
        Validate.notNull(currentState, "The environment returned a null state, which is not allowed");

        Action nextAction = getNextAction();

        updateQuality();

        nextAction.execute();

        this.previousAction = nextAction;
        logger.debug("Set previous action to {}", nextAction);
        this.previousState = currentState;
        logger.debug("Set previous state to {}", currentState);

        logger.debug("---- END OF TICK ---- exited takeNextAction");
    }

    private Action getNextAction() {
        assertSystemCondition(currentState != null,
                "The current state was null at a time that we needed to get the next action");

        Set<Action> possibleActions = currentState.getActions();
        Validate.notNull(possibleActions,
                "The current state returned a null list of next actions, which is not allowed");

        Action nextAction;

        if (shouldExplore()) {
            nextAction = getRandomAction(possibleActions);
            logger.debug("Should explore, chose random action: {}", nextAction);
        } else {
            nextAction = getBestAction(currentState, possibleActions);
            logger.debug("Should not explore, chose best action: {}", nextAction);
        }

        return nextAction;
    }

    private boolean shouldExplore() {
        return (explorationFactor == 1) || ((explorationFactor != 0) && (Math.random() < explorationFactor));
    }

    private Action getBestAction(State state, Set<Action> possibleActions) {
        logger.debug("Determining best action from possible actions: {}", possibleActions);

        SortedSet<ImmutablePair<Double, Action>> actionQualities = new TreeSet<>();

        for (Action action : possibleActions) {
            double actionQuality = getQuality(state, action);
            logger.debug("Got quality for action {}: {}", action, actionQuality);
            actionQualities.add(ImmutablePair.of(actionQuality, action));
        }

        logger.debug("Possible actions and qualities: {}", actionQualities);

        return actionQualities.last().getRight();
    }

    private Action getRandomAction(Set<Action> possibleActions) {
        Validate.notNull(possibleActions);
        Validate.isTrue(!possibleActions.isEmpty());

        List<Action> actions = new ArrayList<>(possibleActions);
        return actions.get(RandomUtils.nextInt(0, actions.size()));
    }

    /**
     * Update the quality of the previous state-action pair, given the desirability of the new state
     * 
     * @param nextState
     */
    private void updateQuality() {
        assertSystemCondition(currentState != null, "The current State of the system was null, cannot continue");

        if (this.previousState == null || this.previousAction == null) {
            logger.debug("Previous state or previous actions is null. We will assume we are at a starting state");
        } else {
            double oldQuality = getQuality(this.previousState, this.previousAction);
            double reward = currentState.getReward();
            double optimalFutureValueEstimate = getBestQuality(currentState);

            logger.debug(
                    "Calculating new quality using the following values: (Qt+1: {}), (a: {}), (Rt+1: {}), (d: {}), (maxQt: {})",
                    oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);
            logger.debug("{} + ({} * ({} + {} * {} - {}))", oldQuality, learningRate, reward, discountFactor,
                    optimalFutureValueEstimate, oldQuality);

            double newQuality = oldQuality
                    + (learningRate * (reward + discountFactor * optimalFutureValueEstimate - oldQuality));

            ImmutablePair<State, Action> pair = ImmutablePair.of(previousState, previousAction);

            logger.debug("Updating quality for {} to {}", pair, newQuality);

            qualities.put(pair, newQuality);
        }
    }

    private double getBestQuality(State s) {
        Validate.notNull(s);

        double bestQualityForState;

        Set<Action> possibleActions = s.getActions();

        if (possibleActions == null || possibleActions.isEmpty()) {
            bestQualityForState = DEFAULT_STATE_ACTION_QUALITY;
        } else {
            bestQualityForState = -Double.MAX_VALUE;
            for (Action action : possibleActions) {
                double qualityForState = getQuality(s, action);
                logger.debug("Potential future quality for state {}, action {}: {}", s, action, qualityForState);
                bestQualityForState = Math.max(bestQualityForState, qualityForState);
            }
        }

        logger.debug("Got best quality for state {}: {}", s, bestQualityForState);
        return bestQualityForState;
    }

    private double getQuality(State s, Action a) {
        double qualityToGet;
        ImmutablePair<State, Action> pair = ImmutablePair.of(s, a);

        if (s == null || a == null) {
            qualityToGet = DEFAULT_STATE_ACTION_QUALITY;

        } else if (qualities.containsKey(pair)) {
            qualityToGet = qualities.get(pair);

        } else {
            qualityToGet = DEFAULT_STATE_ACTION_QUALITY;
        }

        return qualityToGet;
    }

    private void assertSystemCondition(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
