package qlearning.agent;

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
