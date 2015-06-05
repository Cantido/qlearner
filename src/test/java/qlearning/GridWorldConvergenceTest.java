package qlearning;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import qlearning.domain.DiscountFactor;
import qlearning.example.gridworld.GridWorld;
import qlearning.impl.QualityHashMap;
import qlearning.impl.RandomExplorationStrategy;

/**
 * Tests that the {@link GridWorld} implementation of this library converges on the goal state.
 */
public class GridWorldConvergenceTest {

    Agent agent;
    GridWorld gridWorld;
    ExplorationStrategy explorationStrategy;
    QualityMap qualityMap;

    @Before
    public void setUp() {
        explorationStrategy = new RandomExplorationStrategy(0.1);
        
        gridWorld = new GridWorld();
        
        qualityMap = new QualityHashMap();
        
        agent = new Agent();
        agent.setEnvironment(gridWorld);
        agent.setDiscountFactor(new DiscountFactor(1.0));
        agent.setLearningRate(1);
        
        agent.setExplorationStrategy(explorationStrategy);
        agent.setQualityMap(qualityMap);
    }

    /**
     * Tests that the number of steps that we take to reach the goal goes below an observed value. Even though the agent
     * explores a little randomly, we can be fairly certain that the average steps per success goes down below around
     * 50, given the values set in {@link #setUp}. After running this a few times, the average steps per success is
     * around 30, so 50 gives us some wiggle room but can still assert that the agent is learning the space.
     */
    @Test
    public void testConvergence() {
        int totalSteps = 0;
        int totalSuccesses = 0;

        // I increment totalSteps in the loop, instead of assuming it is exactly 10000, because we'll
        // probably end up halfway through an episode once we hit 10000 total steps
        for (int i = 0; i < 10000; i++, totalSteps++) {

            agent.takeNextAction();

            if (gridWorld.isAtGoalState()) {
                totalSuccesses++;
                gridWorld.reset();
                agent.resetState();
            }
        }

        int avgStepsPerSuccess = totalSteps / totalSuccesses;

        assertThat(avgStepsPerSuccess, lessThan(50));
    }

}