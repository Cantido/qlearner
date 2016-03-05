package qlearning.quality.strategy;

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
