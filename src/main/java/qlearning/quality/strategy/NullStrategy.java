package qlearning.quality.strategy;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Reward;
import qlearning.quality.Quality;

/**
 * A {@code QualityUpdateStrategy} that does absolutely nothing
 */
public class NullStrategy implements QualityUpdateStrategy {
    @Override
    public Quality next(
            Quality oldQuality,
            LearningRate learningRate,
            Reward reward,
            DiscountFactor discountFactor,
            Quality optimalFutureValueEstimate) {
        return oldQuality;
    }

}
