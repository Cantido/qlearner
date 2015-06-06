package qlearning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Quality;
import qlearning.domain.Reward;
import qlearning.domain.StateActionQuality;

/**
 * Performs {@link Action}s that will lead to changes in the {@link Environment}'s current {@link State}.
 * <p>
 * This is the object that will navigate and learn the problem space (the {@code Environment}). It implements the
 * Q-learning algorithm to pick the optimal {@code Action} to take while in each {@code State}.
 */
public class Agent {
    private final Environment environment;
    private final ExplorationStrategy explorationStrategy;
    private final LearningRate learningRate;
    private final DiscountFactor discountFactor;
    private final QualityMap qualityMap;
    
    private boolean atFirstEpisode = true;
    
    private State previousState;
    private Action previousAction;

    private State currentState;
    
    private Set<Action> possibleNextActions;
    
    private static Logger logger = LoggerFactory.getLogger(Agent.class);
    
    public Agent(Environment environment,
            ExplorationStrategy explorationStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {

        Validate.notNull(environment, "Environment cannot be null");
        Validate.notNull(explorationStrategy, "LearningRate cannot be null");
        Validate.notNull(learningRate, "DiscountFactor cannot be null");
        Validate.notNull(discountFactor, "ExplorationStrategy cannot be null");
        Validate.notNull(qualityMap, "QualityMap cannot be null");
        
        this.environment = environment;
        this.explorationStrategy = explorationStrategy;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        this.qualityMap = qualityMap;
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

        currentState = environment.getState();

        logger.debug("Got current state: {}", currentState);
        Validate.notNull(currentState, "The environment's state cannot be null");
        
        possibleNextActions = currentState.getActions();
        validatePossibleNextActions();
        
        Collection<StateActionQuality> potentialQualities = buildTriplets(currentState, possibleNextActions);
        
        Action nextAction = explorationStrategy.getNextAction(potentialQualities);
        
        validateNextAction(nextAction);
        
        if (atFirstEpisode) {
            logger.debug("This episode is the algorithm's first, so we cannot update the quality for the previous state & action");
            atFirstEpisode = false;
        } else {
            updateQuality();
        }
        
        logger.debug("Taking next action: {}", nextAction);
        
        nextAction.execute();

        this.previousAction = nextAction;
        this.previousState = currentState;

        logger.debug("---- END OF TICK ---- exited takeNextAction");
    }
    
    private void validatePossibleNextActions() {
        Validate.notNull(possibleNextActions,
                "The list of possible actions from a state cannot be null. " +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        Validate.isTrue(!possibleNextActions.isEmpty(),
                "The list of possible actions from a state cannot be empty. " +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
    }
    
    private void validateNextAction(Action nextAction) {
        Validate.notNull(nextAction, 
                "The action returned by the ExplorationStrategy cannot be null." +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
    }
    
    private Collection<StateActionQuality> buildTriplets(State state, Set<Action> possibleActions) {
        Collection<StateActionQuality> pairs = new ArrayList<>(possibleActions.size());
        
        for(Action action : possibleActions) {
            Quality quality = qualityMap.get(state, action);
            
            pairs.add(new StateActionQuality(state, action, quality));
            
            logger.debug("Potential action: {}, quality: {}", action.toString(), quality.toString());
        }
        
        return pairs;
    }

    /**
     * Update the quality of the previous state-action pair, given the desirability of the new state
     */
    private void updateQuality() {
        Quality oldQuality = qualityMap.get(this.previousState, this.previousAction);
        Reward reward = currentState.getReward();
        Quality optimalFutureValueEstimate = qualityMap.getBestQuality(currentState, possibleNextActions);

        logger.debug(
                "Creating new quality using the following values: (Qt: {}), (a: {}), (Rt+1: {}), (d: {}), (maxQt: {})",
                oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);
        
        Quality newQuality = new Quality(oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);
        

        logger.debug("Updating quality for [{}, {}] to {}", previousState, previousAction, newQuality);

        qualityMap.put(previousState, previousAction, newQuality);
    }
}
