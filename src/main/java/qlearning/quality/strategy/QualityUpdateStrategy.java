package qlearning.quality.strategy;

import qlearning.Action;
import qlearning.State;
import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Reward;
import qlearning.quality.Quality;

/**
 * A strategy for getting the next {@link Quality} value after one learning iteration.
 */
public interface QualityUpdateStrategy {
	/**
	 * Get the next {@link Quality} value
	 * @param oldQuality the quality value of the previous {@link State}-{@link Action} pair
	 * @param learningRate the {@link LearningRate} factor for this update
	 * @param reward the {@link Reward} for the current {@code State}
	 * @param discountFactor the {@link DiscountFactor} for this update
	 * @param optimalFutureValueEstimate the highest expected {@code Quality} that we can get after transition to the next {@code State} 
	 * @return the new {@code Quality} value for this {@code State}
	 */
	public Quality next(
			Quality oldQuality,
			LearningRate learningRate,
			Reward reward,
			DiscountFactor discountFactor,
			Quality optimalFutureValueEstimate);
}
