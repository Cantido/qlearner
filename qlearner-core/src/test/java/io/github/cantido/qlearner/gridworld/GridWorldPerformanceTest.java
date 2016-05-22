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
    GridWorldBuilder builder = new GridWorldBuilder();
    Agent agent = builder.getAgent();
    GridWorldEnvironment environment =
        (GridWorldEnvironment) builder.getEnvironment();

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