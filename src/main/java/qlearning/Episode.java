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
import qlearning.domain.StateActionQuality;

/**
 * One full iteration of the q-learning algorithm, starting at
 * a certain {@link State}, taking an {@link Action}, and updating
 * the {@link Quality} for that pair.
 * 
 * <p>The q-learning algorithm is implemented by calling {@link Episode#proceed(State)}
 * on a series of {@code Episode} objects, passing the new {@link State} to this {@code Episode}
 * to learn the effect of the last {@link Action} taken.  
 */
public abstract class Episode {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected final ExplorationStrategy explorationStrategy;
    protected final LearningRate learningRate;
    protected final DiscountFactor discountFactor;
    protected final QualityMap qualityMap;
    
    protected State currentState;
    
    protected Set<Action> possibleNextActions;
    protected Action chosenNextAction;
    
    protected Episode(
            ExplorationStrategy explorationStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {
        
        this.explorationStrategy = Validate.notNull(explorationStrategy, "LearningRate cannot be null");
        this.learningRate = Validate.notNull(learningRate, "DiscountFactor cannot be null");
        this.discountFactor = Validate.notNull(discountFactor, "ExplorationStrategy cannot be null");
        this.qualityMap = Validate.notNull(qualityMap, "QualityMap cannot be null");
    }
    
    /**
     * Executes one full iteration of the q-learning algorithm.
     * 
     * @param currentState the state to proceed from in this iteration
     * @return an {@code Episode} that represents the next iteration
     */
    public Episode proceed(final State currentState) {
        this.currentState = validateCurrentState(currentState);
        
        possibleNextActions = validatePossibleNextActions(this.currentState.getActions());
        
        Collection<StateActionQuality> potentialQualities = buildTriplets(currentState, possibleNextActions);
        
        chosenNextAction = validateNextAction(explorationStrategy.getNextAction(potentialQualities));
        
        updateQuality();
        
        chosenNextAction.execute();

        return getNextEpisode();
    }
    
    /**
     * Update the {@link Quality} value based on this episode's performance.
     */
    protected abstract void updateQuality();
    
    /**
     * Get the {@code Episode} which represents the next algorithm iteration 
     * @return the next iteration's {@code Episode}
     */
    protected abstract Episode getNextEpisode(); 
    
    private Collection<StateActionQuality> buildTriplets(State state, Set<Action> possibleActions) {
        Collection<StateActionQuality> pairs = new ArrayList<>(possibleActions.size());
        
        for(Action action : possibleActions) {
            Quality quality = qualityMap.get(state, action);
            
            pairs.add(new StateActionQuality(state, action, quality));
            
            logger.debug("Potential action: {}, quality: {}", action.toString(), quality.toString());
        }
        return pairs;
    }
    
    private static State validateCurrentState(State state) {
        return Validate.notNull(state, "Current state cannot be null");
    }
    
    private static Set<Action> validatePossibleNextActions(Set<Action> nextActions) {
        Validate.notNull(nextActions,
                "The list of possible actions from a state cannot be null. " +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        Validate.isTrue(!nextActions.isEmpty(),
                "The list of possible actions from a state cannot be empty. " +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        return nextActions;
    }
    
    /**
     * Validate the given {@link Action} as an action that will be taken in the next iteration
     * @param nextAction the {@code Action} to validate
     * @return the {@code Action} that was validated
     * 
     * @throws NullPointerException of the given {@code Action} is null
     */
    private static Action validateNextAction(Action nextAction) {
        Validate.notNull(nextAction, 
                "The action returned by the ExplorationStrategy cannot be null." +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        return nextAction;
    }
}
