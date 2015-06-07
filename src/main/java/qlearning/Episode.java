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
    
    protected Quality discoveredQuality;
    
    public Episode(
            ExplorationStrategy explorationStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {
        
        Validate.notNull(explorationStrategy, "LearningRate cannot be null");
        Validate.notNull(learningRate, "DiscountFactor cannot be null");
        Validate.notNull(discountFactor, "ExplorationStrategy cannot be null");
        Validate.notNull(qualityMap, "QualityMap cannot be null");
        
        this.explorationStrategy = explorationStrategy;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        this.qualityMap = qualityMap;
    }
    
    /**
     * Executes one full iteration of the q-learning algorithm.
     * 
     * @param currentState the state to proceed from in this iteration
     * @return an {@code Episode} that represents the next iteration
     */
    protected Episode proceed(State currentState) {
        this.currentState = currentState;
        getPossibleNextActions();
        
        Collection<StateActionQuality> potentialQualities = buildTriplets(currentState, possibleNextActions);
        
        chosenNextAction = explorationStrategy.getNextAction(potentialQualities);
        
        updateQuality();
        
        chosenNextAction.execute();

        return getNextEpisode();
    }
    
    protected abstract void updateQuality();
    protected abstract Episode getNextEpisode(); 
    
    protected void getPossibleNextActions() {
        possibleNextActions = currentState.getActions();
        
        Validate.notNull(possibleNextActions,
                "The list of possible actions from a state cannot be null. " +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        Validate.isTrue(!possibleNextActions.isEmpty(),
                "The list of possible actions from a state cannot be empty. " +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
    }
    
    protected void validateChosenNextAction() {
        Validate.notNull(chosenNextAction, 
                "The action returned by the ExplorationStrategy cannot be null." +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
    }

    protected Collection<StateActionQuality> buildTriplets(State state, Set<Action> possibleActions) {
        
        Collection<StateActionQuality> pairs = new ArrayList<>(possibleActions.size());
        
        for(Action action : possibleActions) {
            Quality quality = qualityMap.get(state, action);
            
            pairs.add(new StateActionQuality(state, action, quality));
            
            logger.debug("Potential action: {}, quality: {}", action.toString(), quality.toString());
        }
        return pairs;
    }
}
