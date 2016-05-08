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

import javax.annotation.Nonnull;

import qlearning.agent.Step;
import qlearning.client.Action;
import qlearning.client.State;
import qlearning.domain.learning.DiscountFactor;
import qlearning.domain.learning.LearningRate;
import qlearning.domain.learning.Reward;

public class QualityUpdater {
    @Nonnull private final QualityMap qualityMap;
    @Nonnull private final QualityUpdateStrategy strategy;
    @Nonnull private final LearningRate learningRate;
    @Nonnull private final DiscountFactor discountFactor;

    public QualityUpdater(QualityMap qualityMap, QualityUpdateStrategy strategy, LearningRate learningRate, DiscountFactor discountFactor) {
        this.qualityMap = qualityMap;
        this.strategy = strategy;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
    }

    public void updateQuality(Step stepTaken, State resultingState) {
        State previousState = stepTaken.getStartingState();
        Action actionTaken = stepTaken.getLeavingAction();

        Quality oldQuality = qualityMap.get(previousState, actionTaken);

        Reward reward = resultingState.getReward();
        Quality optimalFutureValueEstimate = qualityMap.getBestQuality(resultingState);

        Quality newQuality = this.strategy.next(oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);

        qualityMap.put(previousState, actionTaken, newQuality);
    }
}
