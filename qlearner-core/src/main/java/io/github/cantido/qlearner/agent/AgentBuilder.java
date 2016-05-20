/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with QLearner. If not,
 * see <http://www.gnu.org/licenses/>.
 */

package io.github.cantido.qlearner.agent;

import io.github.cantido.qlearner.agent.executors.DirectExecutor;
import io.github.cantido.qlearner.algorithm.exploration.RandomExplorationStrategy;
import io.github.cantido.qlearner.algorithm.model.DiscountFactor;
import io.github.cantido.qlearner.algorithm.model.ExplorationFactor;
import io.github.cantido.qlearner.algorithm.model.ExplorationStrategy;
import io.github.cantido.qlearner.algorithm.model.LearningRate;
import io.github.cantido.qlearner.algorithm.model.Quality;
import io.github.cantido.qlearner.algorithm.model.QualityMap;
import io.github.cantido.qlearner.algorithm.model.QualityUpdateStrategy;
import io.github.cantido.qlearner.algorithm.quality.BackwardInduction;
import io.github.cantido.qlearner.algorithm.quality.QualityHashMap;
import io.github.cantido.qlearner.algorithm.quality.QualityUpdater;
import io.github.cantido.qlearner.client.Action;
import io.github.cantido.qlearner.client.Environment;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Builds {@link Agent} objects using the Builder pattern.
 * 
 * <pre>
 * Agent agent = new AgentBuilder().setEnvironment(environment)
 *     .setExplorationStrategy(EXPLORATION_STRATEGY).setLearningRate(LEARNING_RATE)
 *     .setDiscountFactor(DISCOUNT_FACTOR).setQualityMap(qualityMap).getAgent();
 * </pre>
 */
@NotThreadSafe
public class AgentBuilder {
  @Nonnull
  private static final ExplorationFactor DEFAULT_EXPLORATION_FACTOR = new ExplorationFactor(0.2);

  @Nonnull
  private final Environment environment;
  @Nonnull
  private Executor actionExecutor = new DirectExecutor();
  @Nonnull
  private DiscountFactor discountFactor = new DiscountFactor(1);
  @Nonnull
  private ExplorationStrategy explorationStrategy =
      new RandomExplorationStrategy(DEFAULT_EXPLORATION_FACTOR);
  @Nonnull
  private LearningRate learningRate = new LearningRate(1);
  @Nonnull
  private QualityMap qualityMap = new QualityHashMap();
  @Nonnull
  private QualityUpdateStrategy qualityUpdateStrategy = new BackwardInduction();
  @Nonnull
  private QualityUpdater qualityUpdater =
      new QualityUpdater(qualityMap, qualityUpdateStrategy, learningRate, discountFactor);

  /**
   * Start building an {@link Agent} that will interact with the given {@link Environment}. You can
   * immediately call {@link #getAgent()} to get an {@code Agent} populated with default values.
   * 
   * @param environment the environment that the result agent will interact with.
   */
  public AgentBuilder(Environment environment) {
    this.environment = environment;
  }

  /**
   * Get the resulting {@link Agent} that this object is building. Subsequent calls will return new
   * and distinct agent objects.
   * 
   * @return the {@code Agent} that this object was building.
   */
  public Agent getAgent() {
    return new Agent(environment, explorationStrategy, qualityMap, actionExecutor, qualityUpdater);
  }

  /**
   * Get the {@code Executor} that this builder will give to its resulting {@link Agent}. This
   * object could be a default value, or one provided by the user.
   * 
   * @return the {@code Executor} that the resulting {@code Agent} will use.
   */
  public Executor getActionExecutor() {
    return this.actionExecutor;
  }

  /**
   * Get the {@code DiscountFactor} that this builder will give to its resulting {@link Agent}. This
   * object could be a default value, or one provided by the user.
   * 
   * @return the {@code DiscountFactor} that the resulting {@code Agent} will use.
   */
  public DiscountFactor getDiscountFactor() {
    return this.discountFactor;
  }

  /**
   * Get the {@code Environment} that this builder will give to its resulting {@link Agent}. This
   * object was provided by the user on construction.
   * 
   * @return the {@code Environment} that the resulting {@code Agent} will use.
   */
  public Environment getEnvironment() {
    return this.environment;
  }

  /**
   * Get the {@code ExplorationStrategy} that this builder will give to its resulting {@link Agent}.
   * This object could be a default value, or one provided by the user.
   * 
   * @return the {@code ExplorationStrategy} that the resulting {@code Agent} will use.
   */
  public ExplorationStrategy getExplorationStrategy() {
    return this.explorationStrategy;
  }

  /**
   * Get the {@code LearningRate} that this builder will give to its resulting {@link Agent}. This
   * object could be a default value, or one provided by the user.
   * 
   * @return the {@code LearningRate} that the resulting {@code Agent} will use.
   */
  public LearningRate getLearningRate() {
    return this.learningRate;
  }

  /**
   * Get the {@code QualityMap} that this builder will give to its resulting {@link Agent}. This
   * object could be a default value, or one provided by the user.
   * 
   * @return the {@code QualityMap} that the resulting {@code Agent} will use.
   */
  public QualityMap getQualityMap() {
    return this.qualityMap;
  }

  /**
   * Get the {@code QualityUpdateStrategy} that this builder will give to its resulting
   * {@link Agent}. This object could be a default value, or one provided by the user.
   * 
   * @return the {@code QualityUpdateStrategy} that the resulting {@code Agent} will use.
   */
  public QualityUpdateStrategy getQualityUpdateStrategy() {
    return this.qualityUpdateStrategy;
  }

  /**
   * Set the {@code DiscountFactor} that this builder will give to its resulting {@link Agent} to
   * control how it learns.
   * 
   * @param discountFactor the {@code DiscountFactor} that the resulting {@code Agent} will use.
   * @return this builder, for chaining
   */
  public AgentBuilder setDiscountFactor(DiscountFactor discountFactor) {
    this.discountFactor = discountFactor;
    this.qualityUpdater = new QualityUpdater(this.qualityMap, this.qualityUpdateStrategy,
        this.learningRate, discountFactor);
    return this;
  }

  /**
   * Set the {@code Executor} that this builder will give to its resulting {@link Agent} to execute
   * chosen {@link Action}s.
   * 
   * @param executor the {@code Executor} that the resulting {@code Agent} will use.
   * @return this builder, for chaining
   */
  public AgentBuilder setExecutor(Executor executor) {
    this.actionExecutor = executor;
    return this;
  }

  /**
   * Set the {@code ExplorationStrategy} that this builder will give to its resulting {@link Agent}
   * to control how it explores its {@link Environment}.
   * 
   * @param explorationStrategy the {@code ExplorationStrategy} that the resulting {@code Agent}
   *        will use.
   * @return this builder, for chaining
   */
  public AgentBuilder setExplorationStrategy(ExplorationStrategy explorationStrategy) {
    this.explorationStrategy = explorationStrategy;
    return this;
  }

  /**
   * Set the {@code LearningRate} that this builder will give to its resulting {@link Agent} to
   * control how it learns.
   * 
   * @param learningRate the {@code LearningRate} that the resulting {@code Agent} will use.
   * @return this builder, for chaining
   */
  public AgentBuilder setLearningRate(LearningRate learningRate) {
    this.learningRate = learningRate;
    this.qualityUpdater = new QualityUpdater(this.qualityMap, this.qualityUpdateStrategy,
        learningRate, this.discountFactor);
    return this;
  }

  /**
   * Set the {@code QualityMap} that this builder will give to its resulting {@link Agent} to store
   * {@link Quality} values.
   * 
   * @param qualityMap the {@code QualityMap} that the resulting {@code Agent} will use.
   * @return this builder, for chaining
   */
  public AgentBuilder setQualityMap(QualityMap qualityMap) {
    this.qualityMap = qualityMap;
    this.qualityUpdater = new QualityUpdater(qualityMap, this.qualityUpdateStrategy,
        this.learningRate, this.discountFactor);
    return this;
  }

  /**
   * Set the {@code QualityUpdateStrategy} that this builder will give to its resulting
   * {@link Agent} to update {@link Quality} values as it learns.
   * 
   * @param qualityUpdateStrategy the {@code QualityUpdateStrategy} that the resulting {@code Agent}
   *        will use.
   * @return this builder, for chaining
   */
  public AgentBuilder setQualityUpdateStrategy(QualityUpdateStrategy qualityUpdateStrategy) {
    this.qualityUpdateStrategy = qualityUpdateStrategy;
    this.qualityUpdater = new QualityUpdater(this.qualityMap, qualityUpdateStrategy,
        this.learningRate, this.discountFactor);
    return this;
  }
}
