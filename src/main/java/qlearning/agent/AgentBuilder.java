package qlearning.agent;

import qlearning.Environment;
import qlearning.ExplorationStrategy;
import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.quality.map.QualityMap;
import qlearning.quality.strategy.QualityUpdateStrategy;

/**
 * Builds {@link Agent} objects using the Builder pattern:
 * 
 * <pre>
 * Agent agent = 
 *  new AgentBuilder()
 *        .setEnvironment(environment)
 *        .setExplorationStrategy(EXPLORATION_STRATEGY)
 *        .setLearningRate(LEARNING_RATE)
 *        .setDiscountFactor(DISCOUNT_FACTOR)
 *        .setQualityMap(qualityMap)
 *        .getAgent();
 * </pre>
 */
public class AgentBuilder {
    private Environment environment;
    private ExplorationStrategy explorationStrategy;
    private QualityUpdateStrategy qualityUpdateStrategy;
    private LearningRate learningRate;
    private DiscountFactor discountFactor;
    private QualityMap qualityMap;

    public AgentBuilder setEnvironment(Environment environment) {
        this.environment = environment;
        return this;
    }

    public AgentBuilder setExplorationStrategy(ExplorationStrategy explorationStrategy) {
        this.explorationStrategy = explorationStrategy;
        return this;
    }

    public AgentBuilder setQualityUpdateStrategy(QualityUpdateStrategy qualityUpdateStrategy) {
        this.qualityUpdateStrategy = qualityUpdateStrategy;
        return this;
    }

    public AgentBuilder setLearningRate(LearningRate learningRate) {
        this.learningRate = learningRate;
        return this;
    }

    public AgentBuilder setDiscountFactor(DiscountFactor discountFactor) {
        this.discountFactor = discountFactor;
        return this;
    }

    public AgentBuilder setQualityMap(QualityMap qualityMap) {
        this.qualityMap = qualityMap;
        return this;
    }

    public Agent getAgent() {
        return new Agent(
                    environment,
                    explorationStrategy,
                    qualityUpdateStrategy,
                    learningRate,
                    discountFactor,
                    qualityMap);
    }
}
