package qlearning.quality.strategy;

import org.apache.commons.lang3.Validate;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Reward;
import qlearning.quality.Quality;

/**
 * A strategy for updating {@link Quality} values by looking at backwards at
 * the {@link Reward}s accumulated thus far.
 */
public class BackwardInduction implements QualityUpdateStrategy {
	@Override
	public Quality next(Quality oldQuality, LearningRate learningRate, Reward reward, DiscountFactor discountFactor,
			Quality optimalFutureValueEstimate) {
		
        Validate.notNull(oldQuality, "Quality cannot be null");
        Validate.notNull(reward, "Reward cannot be null");
        Validate.notNull(optimalFutureValueEstimate, "Optimal future value estimate cannot be null");
        Validate.notNull(learningRate, "LearningRate cannot be null");
        Validate.notNull(discountFactor, "DiscountFactor cannot be null");
        
        double qualityValue = oldQuality.toDouble()
                				+ (learningRate.toDouble()
                						* (reward.toDouble()
                								+ discountFactor.toDouble() * optimalFutureValueEstimate.toDouble()
                								- oldQuality.toDouble()));
        
        return new Quality(qualityValue);
	}
}
