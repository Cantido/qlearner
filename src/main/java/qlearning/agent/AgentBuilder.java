package qlearning.agent;

import qlearning.Environment;
import qlearning.ExplorationStrategy;
import qlearning.domain.DiscountFactor;
import qlearning.domain.ExplorationFactor;
import qlearning.domain.LearningRate;
import qlearning.impl.RandomExplorationStrategy;
import qlearning.quality.map.QualityHashMap;
import qlearning.quality.map.QualityMap;
import qlearning.quality.strategy.BackwardInduction;
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
    private static final ExplorationFactor DEFAULT_EXPLORATION_FACTOR = new ExplorationFactor(0.2);
    
    private Environment environment = Environment.EMPTY;
    private ExplorationStrategy explorationStrategy = new RandomExplorationStrategy(DEFAULT_EXPLORATION_FACTOR);
    private QualityUpdateStrategy qualityUpdateStrategy = new BackwardInduction();
    private LearningRate learningRate = new LearningRate(1);
    private DiscountFactor discountFactor = new DiscountFactor(1);
    private QualityMap qualityMap = new QualityHashMap();
    
    public AgentBuilder(Environment environment) {
        this.environment = environment;
    }
    
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
