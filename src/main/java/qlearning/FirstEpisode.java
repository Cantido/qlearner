package qlearning;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Quality;

/**
 * An iteration of the q-learning algorithm that does not have a {@link State} or {@link Action}
 * from which to update a {@link Quality} value. This class should only be used at the
 * beginning of 
 * 
 * <p>Calling {@link #proceed(State)} on this object will not update any {@code Quality}
 * values, because there is no previous {@code State} or {@code Action} to update
 * a {@code Quality} for. It will, however, return a new {@code Episode} with that data.
 */
public class FirstEpisode extends Episode {
    
    /**
     * Create an {@code Episode} that does not have a previous {@link State} or {@link Action}
     * from which to build a {@link Quality} value.
     * 
     * @param explorationStrategy
     *          The {@link ExplorationStrategy} that this and future {@code Episode} objects will use
     *          to determine when to explore
     * @param learningRate
     *          The {@link LearningRate} that this and future {@code Episode} objects will use
     *          to update {@code Quality} values
     * @param discountFactor The {@link DiscountFactor} that this and future {@code Episode} objects will use
     *          to update {@code Quality} values
     * @param qualityMap The {@link QualityMap} that this and future {@code Episode} objects will use
     *          to get and update {@code Quality} values
     */
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
