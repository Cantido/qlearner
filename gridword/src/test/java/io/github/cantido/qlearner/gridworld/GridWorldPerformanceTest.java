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
import io.github.cantido.qlearner.gridworld.GridWorldEnvironment;

import java.time.Duration;
import java.time.Instant;

/**
 * A test with a high number of iterations, used for performance testing an optimization.
 */
public class GridWorldPerformanceTest {
  private static final int MAX_SUCCESS_COUNT = 500000;

  /**
   * Build a {@link GridWorldEnvironment} and an {@link Agent}, and run a large number of
   * iterations.
   * 
   * @param args not used.
   */
  public static void main(String[] args) {
    GridWorldEnvironment environment = new GridWorldEnvironment(10, 10, 0, 0, 10, 10);
    QualityMap qualityMap = new QualityHashMap(100, 4);
    QualityUpdateStrategy qualityUpdateStrategy = new BackwardInduction();

    ExplorationFactor EXPLORATION_FACTOR = new ExplorationFactor(0.1);
    ExplorationStrategy EXPLORATION_STRATEGY = new RandomExplorationStrategy(EXPLORATION_FACTOR);
    DiscountFactor DISCOUNT_FACTOR = new DiscountFactor(1);
    LearningRate LEARNING_RATE = new LearningRate(1);


    Agent agent = (new AgentBuilder(environment))
        .setExplorationStrategy(EXPLORATION_STRATEGY)
        .setLearningRate(LEARNING_RATE)
        .setDiscountFactor(DISCOUNT_FACTOR)
        .setQualityMap(qualityMap)
        .setQualityUpdateStrategy(qualityUpdateStrategy)
        .getAgent();

    Instant start = Instant.now();
    System.out.println("Running " + MAX_SUCCESS_COUNT + " successes, starting " + start.toString());

    for (int successCount = 0; successCount < MAX_SUCCESS_COUNT; successCount++) {

      while (!environment.isAtGoalState()) {
        agent.takeNextAction();
      }

      environment.reset();
      agent.reset();
    }

    Instant end = Instant.now();

    Duration runTime = Duration.between(start, end).abs();

    System.out.println("Finished at " + end + ". Total time taken: " + runTime);
  }
}
