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

package qlearning.agent;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;

import qlearning.agent.executors.DirectExecutor;
import qlearning.client.Environment;
import qlearning.domain.exploration.ExplorationFactor;
import qlearning.domain.exploration.ExplorationStrategy;
import qlearning.domain.exploration.RandomExplorationStrategy;
import qlearning.domain.learning.DiscountFactor;
import qlearning.domain.learning.LearningRate;
import qlearning.domain.quality.BackwardInduction;
import qlearning.domain.quality.QualityHashMap;
import qlearning.domain.quality.QualityMap;
import qlearning.domain.quality.QualityUpdateStrategy;
import qlearning.domain.quality.QualityUpdater;

/**
 * Builds {@link Agent} objects using the Builder pattern.
 * 
 * <pre>
 * Agent agent = new AgentBuilder().setEnvironment(environment).setExplorationStrategy(EXPLORATION_STRATEGY)
 * 		.setLearningRate(LEARNING_RATE).setDiscountFactor(DISCOUNT_FACTOR).setQualityMap(qualityMap).getAgent();
 * </pre>
 */
public class AgentBuilder {
	@Nonnull private static final ExplorationFactor DEFAULT_EXPLORATION_FACTOR = new ExplorationFactor(0.2);

	@Nonnull private final Environment environment;
	@Nonnull private Executor actionExecutor = new DirectExecutor();
	@Nonnull private DiscountFactor discountFactor = new DiscountFactor(1);
	@Nonnull private ExplorationStrategy explorationStrategy = new RandomExplorationStrategy(DEFAULT_EXPLORATION_FACTOR);
	@Nonnull private LearningRate learningRate = new LearningRate(1);
	@Nonnull private QualityMap qualityMap = new QualityHashMap();
	@Nonnull private QualityUpdateStrategy qualityUpdateStrategy = new BackwardInduction();
	@Nonnull private QualityUpdater qualityUpdater = new QualityUpdater(qualityMap, qualityUpdateStrategy, learningRate, discountFactor);

	public AgentBuilder(Environment environment) {
		this.environment = environment;
	}

	public Agent getAgent() {
		return new Agent(environment, explorationStrategy, qualityMap, actionExecutor, qualityUpdater);
	}

	public Executor getActionExecutor() {
		return this.actionExecutor;
	}

	public DiscountFactor getDiscountFactor() {
		return this.discountFactor;
	}

	public Environment getEnvironment() {
		return this.environment;
	}

	public ExplorationStrategy getExplorationStrategy() {
		return this.explorationStrategy;
	}

	public LearningRate getLearningRate() {
		return this.learningRate;
	}

	public QualityMap getQualityMap() {
		return this.qualityMap;
	}

	public QualityUpdateStrategy getQualityUpdateStrategy() {
		return this.qualityUpdateStrategy;
	}

	public void setActionExecutor(Executor actionExecutor) {
		this.actionExecutor = actionExecutor;
	}

	public AgentBuilder setDiscountFactor(DiscountFactor discountFactor) {
		this.discountFactor = discountFactor;
		this.qualityUpdater = new QualityUpdater(
				this.qualityMap,
				this.qualityUpdateStrategy,
				this.learningRate,
				discountFactor);
		return this;
	}

	public AgentBuilder setExecutor(Executor executor) {
		this.actionExecutor = executor;
		return this;
	}

	public AgentBuilder setExplorationStrategy(ExplorationStrategy explorationStrategy) {
		this.explorationStrategy = explorationStrategy;
		return this;
	}

	public AgentBuilder setLearningRate(LearningRate learningRate) {
		this.learningRate = learningRate;
		this.qualityUpdater = new QualityUpdater(
				this.qualityMap,
				this.qualityUpdateStrategy,
				learningRate,
				this.discountFactor);
		return this;
	}

	public AgentBuilder setQualityMap(QualityMap qualityMap) {
		this.qualityMap = qualityMap;
		this.qualityUpdater = new QualityUpdater(
				qualityMap,
				this.qualityUpdateStrategy,
				this.learningRate,
				this.discountFactor);
		return this;
	}

	public AgentBuilder setQualityUpdateStrategy(QualityUpdateStrategy qualityUpdateStrategy) {
		this.qualityUpdateStrategy = qualityUpdateStrategy;
		this.qualityUpdater = new QualityUpdater(
				this.qualityMap,
				qualityUpdateStrategy,
				this.learningRate,
				this.discountFactor);
		return this;
	}
}