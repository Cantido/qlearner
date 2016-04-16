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

import org.apache.commons.lang3.Validate;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Reward;
import qlearning.domain.quality.Quality;
import qlearning.domain.quality.QualityUpdateStrategy;

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
