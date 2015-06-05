package qlearning;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.impl.QualityHashMap;

/**
 * Performs {@link Action}s that will lead to changes in the {@link Environment}'s current {@link State}.
 * <p>
 * This is the object that will navigate and learn the problem space (the {@code Environment}). It implements the
 * Q-learning algorithm to pick the optimal {@code Action} to take while in each {@code State}.
 */
public class Agent {
    private DiscountFactor discountFactor = new DiscountFactor(DOUBLE_ONE);
    private LearningRate learningRate = new LearningRate(DOUBLE_ONE);
    
    private QualityMap qualityMap;
    private Environment environment;
    private ExplorationStrategy explorationStrategy;
    
    private boolean atFirstEpisode = true;
    
    private State previousState;
    private Action previousAction;

    private State currentState;
    
    private Set<Action> possibleNextActions;

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

    public void setLearningRate(LearningRate lr) {
        Validate.notNull(lr, "LearningRate cannot be null");
        this.learningRate = lr;
    }

    public void setDiscountFactor(DiscountFactor df) {
        this.discountFactor = df;
    }
    
    public void setExplorationStrategy(ExplorationStrategy strategy) {
        Validate.notNull(strategy, "ExplorationStrategy cannot be null");
        this.explorationStrategy = strategy;
    }
    
    public void setQualityMap(QualityMap map) {
        Validate.notNull(map, "QualityMap cannot be null");
        this.qualityMap = map;
    }
    
    public QualityMap getQualityMap() {
        return this.qualityMap;
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
        this.atFirstEpisode = true;
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

        assertSystemCondition(this.environment != null, "Current environment is null, cannot take action.");
        assertSystemCondition(this.explorationStrategy != null, "Current exploration strategy is null, cannot take action");
        assertSystemCondition(this.qualityMap != null, "Current quality mapping is null, cannot take action");
        
        currentState = environment.getState();

        logger.debug("Got current state: {}", currentState);
        Validate.notNull(currentState, "The environment's state cannot be null");
        
        possibleNextActions = currentState.getActions();
        
        Validate.notNull(possibleNextActions, "The list of possible actions from a state cannot be null");
        Validate.isTrue(!possibleNextActions.isEmpty(),
                "The list of possible actions from a state cannot be empty." +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        
        Map<Pair<State, Action>, Double> pairs = buildPairs(currentState, possibleNextActions);

        Action nextAction = explorationStrategy.getNextAction(pairs);
        
        Validate.notNull(nextAction, 
                "The action returned by the ExplorationStrategy cannot be null." +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        
        
        if (atFirstEpisode) {
            logger.debug("This episode is the algorithm's first, so we cannot update the quality for the previous state & action");
            atFirstEpisode = false;
        } else {
            updateQuality();
        }
        
        nextAction.execute();

        this.previousAction = nextAction;
        logger.debug("Set previous action to {}", nextAction);
        this.previousState = currentState;
        logger.debug("Set previous state to {}", currentState);

        logger.debug("---- END OF TICK ---- exited takeNextAction");
    }
    
    private Map<Pair<State, Action>, Double> buildPairs(State state, Set<Action> possibleActions) {
        Map<Pair<State, Action>, Double> pairs = new HashMap<>(possibleActions.size());
        
        for(Action action : possibleActions) {
            double quality = qualityMap.get(state, action);
            Pair<State, Action> pair = ImmutablePair.of(state, action);
            
            pairs.put(pair, quality);
        }
        
        return pairs;
    }

    /**
     * Update the quality of the previous state-action pair, given the desirability of the new state
     * 
     * @param nextState
     */
    private void updateQuality() {
        double oldQuality = qualityMap.get(this.previousState, this.previousAction);
        double reward = currentState.getReward();
        double optimalFutureValueEstimate = estimateOptimalFutureValue(currentState, possibleNextActions);

        logger.debug(
                "Calculating new quality using the following values: (Qt+1: {}), (a: {}), (Rt+1: {}), (d: {}), (maxQt: {})",
                oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);
        logger.debug("{} + ({} * ({} + {} * {} - {}))", oldQuality, learningRate, reward, discountFactor,
                optimalFutureValueEstimate, oldQuality);

        double newQuality = oldQuality
                + (learningRate.getValue() * (reward + discountFactor.getValue() * optimalFutureValueEstimate - oldQuality));

        logger.debug("Updating quality for [{}, {}] to {}", previousState, previousAction, newQuality);

        qualityMap.put(previousState, previousAction, newQuality);
    }
    
    private double estimateOptimalFutureValue(State state, Set<Action> actions) {
        double bestQualityForState = QualityMap.MIN_QUALITY;

        for (Action action : actions) {
            double qualityForState = qualityMap.get(state, action);
            logger.debug("Potential future quality for state {}, action {}: {}", state, action, qualityForState);
            bestQualityForState = Math.max(bestQualityForState, qualityForState);
        }

        logger.debug("Got best quality for state {}: {}", state, bestQualityForState);
        return bestQualityForState;
    }


    private void assertSystemCondition(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
