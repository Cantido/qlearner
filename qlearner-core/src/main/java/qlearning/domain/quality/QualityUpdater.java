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
