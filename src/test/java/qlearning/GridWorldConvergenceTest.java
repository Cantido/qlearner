package qlearning;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GridWorldConvergenceTest {
	
	Agent agent;
	GridWorld gridWorld;
	
	@Before
	public void setUp() {
		gridWorld = new GridWorld();
		agent = new Agent();
		agent.setEnvironment(gridWorld); 
		agent.setDiscountFactor(1);
		agent.setLearningRate(1);
		agent.setExplorationFactor(0.1);
	}
	
	/**
	 * Tests that the number of steps that we take to reach the goal goes below an observed value.
	 * 
	 * Even though the agent explores a little randomly, we can be fairly certain that the 
	 */
	@Test
	public void testConvergence() {
		int episodeTicks = 0;
		
		int totalSteps = 0;
		int totalSuccesses = 0;
		
		for (int i = 0; i < 10000; i++, episodeTicks++, totalSteps++) {
			
			agent.takeNextAction();
			
			if(gridWorld.isAtGoalState()) {
				totalSuccesses++;
				gridWorld.reset();
				agent.resetState();
				episodeTicks = 0;
			}
		}
		
		int avgStepsPerSuccess = totalSteps / totalSuccesses;
		
		
	}

}
