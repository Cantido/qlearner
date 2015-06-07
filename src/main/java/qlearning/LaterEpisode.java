package qlearning;

import org.apache.commons.lang3.Validate;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Quality;
import qlearning.domain.Reward;

public class LaterEpisode extends Episode {
    protected Action previousAction;
    protected State previousState;
    
    public LaterEpisode(
            State previousState,
            Action previousAction,
            
            ExplorationStrategy explorationStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {
        
        super(explorationStrategy, learningRate, discountFactor, qualityMap);
        
        Validate.notNull(previousState);
        Validate.notNull(previousAction);

        this.previousState = previousState;
        this.previousAction = previousAction;
    }
    
    
    /**
     * Update the quality of the previous state-action pair, given the desirability of the new state
     */
    @Override
    protected void updateQuality() {
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

    @Override
    protected Episode getNextEpisode() {
        
        return new LaterEpisode(currentState, chosenNextAction, explorationStrategy, learningRate, discountFactor, qualityMap);
    }
}