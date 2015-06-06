package qlearning.example.gridworld;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import qlearning.Agent;
import qlearning.ExplorationStrategy;
import qlearning.QualityMap;
import qlearning.domain.DiscountFactor;
import qlearning.domain.ExplorationFactor;
import qlearning.domain.LearningRate;
import qlearning.example.gridworld.GridWorldEnvironment;
import qlearning.impl.QualityHashMap;
import qlearning.impl.RandomExplorationStrategy;

/**
 * Tests that the {@link GridWorldEnvironment} implementation of this library converges on the goal state.
 */
public class GridWorldConvergenceTest {

    Agent agent;
    GridWorldEnvironment gridWorld;
    ExplorationFactor explorationFactor;
    ExplorationStrategy explorationStrategy;
    QualityMap qualityMap;
    DiscountFactor discountFactor;
    LearningRate learningRate;

    @Before
    public void setUp() {
        agent = new Agent();
        
        gridWorld = new GridWorldEnvironment();
        agent.setEnvironment(gridWorld);
        
        discountFactor = new DiscountFactor(1);
        agent.setDiscountFactor(discountFactor);
        
        learningRate = new LearningRate(1);
        agent.setLearningRate(learningRate);
        
        explorationFactor = new ExplorationFactor(0.1);
        explorationStrategy = new RandomExplorationStrategy(explorationFactor);
        agent.setExplorationStrategy(explorationStrategy);

        qualityMap = new QualityHashMap();
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
