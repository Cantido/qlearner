package io.github.cantido.qlearner.gridworld;

import io.github.cantido.qlearner.agent.Agent;
import io.github.cantido.qlearner.agent.AgentBuilder;
import io.github.cantido.qlearner.algorithm.exploration.RandomExplorationStrategy;
import io.github.cantido.qlearner.algorithm.model.DiscountFactor;
import io.github.cantido.qlearner.algorithm.model.ExplorationFactor;
import io.github.cantido.qlearner.algorithm.model.ExplorationStrategy;
import io.github.cantido.qlearner.algorithm.model.LearningRate;
import io.github.cantido.qlearner.algorithm.model.QualityMap;
import io.github.cantido.qlearner.algorithm.model.QualityUpdateStrategy;
import io.github.cantido.qlearner.algorithm.quality.BackwardInduction;
import io.github.cantido.qlearner.algorithm.quality.QualityHashMap;

import javax.annotation.Nullable;

/**
 * Builds an {@link Agent} and provides all fields used to build a GridWorld for testing. 
 */
public class GridWorldBuilder {
  private QualityUpdateStrategy qualityUpdateStrategy = new BackwardInduction();
  private ExplorationFactor explorationFactor = new ExplorationFactor(0.1);
  private ExplorationStrategy explorationStrategy =
      new RandomExplorationStrategy(explorationFactor);
  private DiscountFactor discountFactor = new DiscountFactor(1);
  private LearningRate learningRate = new LearningRate(1);
  
  @Nullable
  private GridWorldEnvironment lastEnvironment;
  
  /**
   * Builds an {@code Agent} with references to a new {@link GridWorldEnvironment},
   * a new {@link QualityMap}, and all parameters provided to this builder.
   * 
   * @return an {@code Agent} constructed with new resources.
   */
  public Agent buildGridWorldAgent() {
    GridWorldEnvironment environment = new GridWorldEnvironment(10, 10, 0, 0, 10, 10);
    lastEnvironment = environment;
    QualityMap qualityMap = new QualityHashMap(100, 4);
    
    return (new AgentBuilder(environment))
        .setExplorationStrategy(explorationStrategy)
        .setLearningRate(learningRate)
        .setDiscountFactor(discountFactor)
        .setQualityMap(qualityMap)
        .setQualityUpdateStrategy(qualityUpdateStrategy)
        .getAgent();
  }
  
  /**
   * Get the {@code GridWorldEnvironment} that the most recent {@link Agent} was created with.
   * @return the most recently-created {@code GridWorldEnvironment}.
   */
  @Nullable
  public GridWorldEnvironment getLastEnvironment() {
    return this.lastEnvironment;
  }
}
