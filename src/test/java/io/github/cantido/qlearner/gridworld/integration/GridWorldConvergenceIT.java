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

package io.github.cantido.qlearner.gridworld.integration;

import static org.apache.commons.math3.stat.StatUtils.percentile;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

import io.github.cantido.qlearner.agent.Agent;
import io.github.cantido.qlearner.gridworld.client.GridWorldBuilder;
import io.github.cantido.qlearner.gridworld.client.GridWorldEnvironment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Tests that the {@link GridWorldEnvironment} implementation of this library converges on the goal
 * state.
 */
public class GridWorldConvergenceIT {

  /**
   * The time limit for these tests.
   * 
   * <p>
   * If there is a problem with the Q-learning algorithm, the values will never converge. That will
   * cause the tests to run too long. If this test fails due to a timeout, we can be pretty sure
   * that the algorithm is not converging as it should.
   * </p>
   */
  @Rule
  public Timeout timeout = Timeout.seconds(1);

  private final GridWorldBuilder gridWorldBuilder = new GridWorldBuilder();

  private Agent agent = gridWorldBuilder.getAgent();
  private GridWorldEnvironment environment =
      (GridWorldEnvironment) gridWorldBuilder.getEnvironment();

  /**
   * We can reasonably expect the agent to learn the environment after this many successful
   * sessions.
   */
  private static final double SUCCESSES_UNTIL_CONVERGENCE = 10;

  /**
   * The "correct" solution to our gridworld takes exactly 20 steps. After a learning period, the
   * agent should take this many steps most of the time.
   */
  private static final double EXACT_SOLUTION = 20;

  /**
   * How many successes we want to run this test for.
   */
  private static final int MAX_SUCCESS_COUNT = 1000;

  /**
   * The percentage of successes that have step counts above {@link #EXACT_SOLUTION}.
   */
  private static final double CONVERGENCE_PERCENTAGE =
      SUCCESSES_UNTIL_CONVERGENCE / MAX_SUCCESS_COUNT;


  /**
   * Tests that the number of steps that we take to reach the goal goes below an observed value.
   * <p>
   * This will run the agent until it reaches the goal a certain number of times, and will calculate
   * the average number of steps it took to reach the goal state.
   * </p><p>
   * There is the potential for the agent to never find the goal state, and for this test to take an
   * infinite amount of time. For that reason, {@link #timeout} has been set up to guard us from
   * that.
   * </p>
   */
  @Test
  public void reachesHighSuccessRate() {
    // I know the fastest way to do this would be to just divide totalSteps
    // by totalSuccesses, but creating a double array allows us to do some
    // more fancy statistics, if I ever write tests for that.

    double[] stepCounts = new double[MAX_SUCCESS_COUNT];

    for (int successCount = 0; successCount < MAX_SUCCESS_COUNT; successCount++) {
      int stepCount = 0;

      while (!environment.isAtGoalState()) {
        agent.takeNextAction();
        stepCount++;
      }

      stepCounts[successCount] = stepCount;
      environment.reset();
      agent.reset();
    }

    assertThat(percentile(stepCounts, CONVERGENCE_PERCENTAGE), lessThanOrEqualTo(EXACT_SOLUTION));
  }
}
