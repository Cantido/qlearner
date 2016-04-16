package qlearning.domain.quality;

/*
 * #%L
 * QLearner
 * %%
 * Copyright (C) 2012 - 2016 Robert Richter
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import qlearning.client.Action;
import qlearning.client.State;
import qlearning.domain.learning.DiscountFactor;
import qlearning.domain.learning.LearningRate;
import qlearning.domain.learning.Reward;

/**
 * A strategy for getting the next {@link Quality} value after one learning iteration.
 */
public interface QualityUpdateStrategy {
    /**
     * A quality update strategy that always returns the original quality value
     */
    public static QualityUpdateStrategy NONE = new QualityUpdateStrategy() { /* Use default behavior */ };
    
	/**
	 * Get the next {@link Quality} value
	 * @param oldQuality the quality value of the previous {@link State}-{@link Action} pair
	 * @param learningRate the {@link LearningRate} factor for this update
	 * @param reward the {@link Reward} for the current {@code State}
	 * @param discountFactor the {@link DiscountFactor} for this update
	 * @param optimalFutureValueEstimate the highest expected {@code Quality} that we can get after transition to the next {@code State} 
	 * @return the new {@code Quality} value for this {@code State}
	 */
	@SuppressWarnings("unused")
    public default Quality next(
			Quality oldQuality,
			LearningRate learningRate,
			Reward reward,
			DiscountFactor discountFactor,
			Quality optimalFutureValueEstimate) {
	    return oldQuality;
	}
}
