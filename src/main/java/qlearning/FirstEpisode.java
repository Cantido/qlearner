package qlearning;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Quality;

/**
 * An iteration of the q-learning algorithm that does not have a {@link State} or {@link Action}
 * from which to update a {@link Quality} value.
 */
public class FirstEpisode extends Episode {
    
    public FirstEpisode(
            ExplorationStrategy explorationStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {
        super(explorationStrategy, learningRate, discountFactor, qualityMap);
    }
    
    @Override
    protected Episode getNextEpisode() {
        return new LaterEpisode(currentState, chosenNextAction, explorationStrategy, learningRate, discountFactor, qualityMap);
    }

    @Override
    protected void updateQuality() {
        // Intentionally left blank. 
    }
}
