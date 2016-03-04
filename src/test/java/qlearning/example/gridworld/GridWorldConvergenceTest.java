/**
 * This file is part of qlearner
 *
 *  Qlearner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Qlearner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Qlearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.example.gridworld;

import static org.junit.Assert.*;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;

import static org.apache.commons.math3.stat.StatUtils.*;

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import qlearning.ExplorationStrategy;
import qlearning.agent.Agent;
import qlearning.agent.AgentBuilder;
import qlearning.domain.DiscountFactor;
import qlearning.domain.ExplorationFactor;
import qlearning.domain.LearningRate;
import qlearning.example.gridworld.GridWorldEnvironment;
import qlearning.impl.RandomExplorationStrategy;
import qlearning.quality.map.QualityHashMap;
import qlearning.quality.map.QualityMap;
import qlearning.quality.strategy.BackwardInduction;
import qlearning.quality.strategy.QualityUpdateStrategy;

/**
 * Tests that the {@link GridWorldEnvironment} implementation of this library
 * converges on the goal state.
 */
@NonNullByDefault({})
public class GridWorldConvergenceTest {

	@Rule
	public Timeout timeout = Timeout.seconds(1);

	private Agent agent;
	
	@NonNull private final GridWorldEnvironment environment = new GridWorldEnvironment();
	@NonNull private final QualityMap qualityMap = new QualityHashMap();
	@NonNull private final QualityUpdateStrategy qualityUpdateStrategy = new BackwardInduction();
	
	@NonNull private static final ExplorationFactor EXPLORATION_FACTOR = new ExplorationFactor(0.1);
	@NonNull private static final ExplorationStrategy EXPLORATION_STRATEGY = new RandomExplorationStrategy(EXPLORATION_FACTOR);
	@NonNull private static final DiscountFactor DISCOUNT_FACTOR = new DiscountFactor(1);
	@NonNull private static final LearningRate LEARNING_RATE = new LearningRate(1);

	/**
	 * We can reasonably expect the agent to learn the environment after this
	 * many successful sessions
	 */
	private static final double SUCCESSES_UNTIL_CONVERGENCE = 10;

	/**
	 * The "correct" solution to our gridworld takes exactly 20 steps. After a
	 * learning period, the agent should take this many steps most of the time.
	 */
	private static final double EXACT_SOLUTION = 20;
	
	/**
	 * How many successes we want to run this test for
	 */
	private static final int MAX_SUCCESS_COUNT = 1000;
	
	/**
	 * The percentage of all sessions that should have step counts below {@link #EXACT_SOLUTION}
	 */
	private static final double CONVERGENCE_PERCENTAGE = SUCCESSES_UNTIL_CONVERGENCE / MAX_SUCCESS_COUNT;

	@Before
	public void setUp() {
		agent = (new AgentBuilder(environment))
					.setExplorationStrategy(EXPLORATION_STRATEGY)
					.setLearningRate(LEARNING_RATE)
					.setDiscountFactor(DISCOUNT_FACTOR)
					.setQualityMap(qualityMap)
					.setQualityUpdateStrategy(qualityUpdateStrategy)
					.getAgent();
	}

	/**
	 * Tests that the number of steps that we take to reach the goal goes below
	 * an observed value.
	 * 
	 * This will run the agent until it reaches the goal a certain number of
	 * times ({@link #MAX_SUCCESS_COUNT}), and will calculate the average number
	 * of steps it took to reach the goal state. The average number of steps
	 * that it takes to reach the goal state must end up under
	 * {@link #MAX_AVERAGE_STEPS_PER_SUCCESS}.
	 * 
	 * There is the potential for the agent to never find the goal state, and
	 * for this test to take an infinite amount of time. For that reason,
	 * {@link #timeout} has been set up to guard us from that.
	 */
	@Test
	public void reachesHighSuccessRate() {
		// I know the fastest way to do this would be to just divide totalSteps
		// by totalSuccesses, but
		// creating a double array allows us to do some more fancy statistics,
		// if I ever write tests for that.

		double[] stepCounts = new double[MAX_SUCCESS_COUNT];
		int successCount = 0;

		for (successCount = 0; successCount < MAX_SUCCESS_COUNT; successCount++) {
			int stepCount = 0;

			while (!environment.isAtGoalState()) {
				agent.takeNextAction();
				stepCount++;
			}

			stepCounts[successCount] = stepCount;
			environment.reset();
			agent.resetState();
		}

		assertThat(percentile(stepCounts, CONVERGENCE_PERCENTAGE), lessThanOrEqualTo(EXACT_SOLUTION));
	}
}
