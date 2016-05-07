/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QLearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.domain.quality;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.domain.learning.DiscountFactor;
import qlearning.domain.learning.LearningRate;
import qlearning.domain.learning.Reward;

/**
 * A strategy for updating {@link Quality} values by looking at backwards at
 * the {@link Reward}s accumulated thus far.
 */
public class BackwardInduction implements QualityUpdateStrategy {
    private final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public Quality next(
			Quality oldQuality,
			LearningRate learningRate,
			Reward reward,
			DiscountFactor discountFactor,
			Quality optimalFutureValueEstimate) {

		logger.debug(
				"Creating new quality using the following values: (Qt: {}), (a: {}), (Rt+1: {}), (d: {}), (maxQt: {})",
				oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);

        double qualityValue = oldQuality.doubleValue()
                				+ (learningRate.doubleValue()
                						* (reward.doubleValue()
                								+ discountFactor.doubleValue() * optimalFutureValueEstimate.doubleValue()
                								- oldQuality.doubleValue()));

        return new Quality(qualityValue);
	}
}