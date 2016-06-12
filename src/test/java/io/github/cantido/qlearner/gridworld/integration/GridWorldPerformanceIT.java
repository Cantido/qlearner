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

package io.github.cantido.qlearner.gridworld.integration;

import io.github.cantido.qlearner.agent.Agent;
import io.github.cantido.qlearner.gridworld.client.GridWorldBuilder;
import io.github.cantido.qlearner.gridworld.client.GridWorldEnvironment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

/**
 * A test with a high number of iterations, used for performance testing an optimization.
 */
public class GridWorldPerformanceIT {
  private static final Logger LOGGER = LoggerFactory.getLogger(GridWorldPerformanceIT.class);
  
  /*
   * Just some math to allow us to choose how long to run the tests for.
   * 
   * Set DESIRED_TEST_DURATION_SECONDS to however long you want to run this test for.
   * Everything else should work itself out.
   * 
   * You may have to tweak EXPECTED_OPERATIONS_PER_SECOND depending on your machine. The number
   * What I've supplied is what approximately works on my machine.
   */
  
  private static final int DESIRED_TEST_DURATION_SECONDS = 10;
  
  private static final int EXPECTED_OPERATIONS_PER_SECOND = 50_000;
  private static final int TIMEOUT = (int) (DESIRED_TEST_DURATION_SECONDS * 1.5);
  
  @Rule public Timeout timeout = Timeout.seconds(TIMEOUT);
  private static final int MAX_SUCCESS_COUNT =
      EXPECTED_OPERATIONS_PER_SECOND * DESIRED_TEST_DURATION_SECONDS;
//  private static final int MAX_SUCCESS_COUNT = 1000;
  /**
   * Build a {@link GridWorldEnvironment} and an {@link Agent}, and run a large number of
   * iterations.
   * 
   * @param args not used.
   * @throws Exception if any exception occurs.
   */
  public static void main(String[] args) throws Exception {
    GridWorldPerformanceIT test = new GridWorldPerformanceIT();
    
    test.performanceTest();
  }
  
  /**
   * Build a {@link GridWorldEnvironment} and an {@link Agent}, and run a large number of
   * iterations.
   * @throws Exception on any exception
   */
  @Test
  public void performanceTest() throws Exception {
    GridWorldBuilder builder = new GridWorldBuilder();
    Agent agent = builder.getAgent();
    GridWorldEnvironment environment =
        (GridWorldEnvironment) builder.getEnvironment();

    Instant start = Instant.now();
    LOGGER.info("Performing {} successes, starting at {}", MAX_SUCCESS_COUNT, start);

    for (int successCount = 0; successCount < MAX_SUCCESS_COUNT; successCount++) {

      while (!environment.isAtGoalState()) {
        agent.takeNextAction();
      }

      environment.reset();
      agent.reset();
    }

    Instant end = Instant.now();

    Duration runTime = Duration.between(start, end).abs();

    LOGGER.info("Finished at {}. Total time taken: {}", end, runTime);
  }
}
