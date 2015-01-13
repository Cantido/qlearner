package qlearning;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;


import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.Action;
import qlearning.Environment;
import qlearning.State;

public class GridWorld implements Environment {
	public static void main(String[] args) {
		System.out.println("Starting GridWorld experiment main log");
		
		Agent agent = new Agent();
		GridWorld gridWorld = new GridWorld();
		agent.setEnvironment(gridWorld); 
		agent.setDiscountFactor(1);
		agent.setLearningRate(1);
		
		
		int episodeTicks = 0;
		
		for (int i = 0; i < 10000; i++, episodeTicks++) {
			//System.out.println("Episode tick " + i + ", " + gridWorld.toString());
			
			agent.takeNextAction();
			
			if(gridWorld.isAtGoalState()) {
				System.out.println("Goal has been reached!, took " + episodeTicks + " steps.");
				gridWorld.reset();
				agent.resetState();
				episodeTicks = 0;
			}
		}
	}
	
	Logger logger = LoggerFactory.getLogger("qlearner.GridWorld");
	
	
	
	private int maxX = 10;
	private int maxY = 10;
	
	private int minX = 0;
	private int minY = 0;
	
	private int startX = 0;
	private int startY = 0;
	
	private int goalX = 10;
	private int goalY = 10;
	
	private int xState = startX;
	private int yState = startY;
	
	public GridWorld() {
		GridWorldAction.setGridWorld(this);
	}
	
	public void setSize(int totalX, int totalY) {
		this.maxX = totalX;
		this.maxY = totalY;
	}
	
	public void setStart(int x, int y) {
		this.startX = x;
		this.startY = y;
	}
	
	public void setGoal(int x, int y) {
		this.goalX = x;
		this.goalY = y;
	}
	
	public boolean isAtGoalState() {
		return (xState == goalX && yState == goalY);
	}
	
	public void reset() {
		xState = startX;
		yState = startY;
	}
	
	public void moveUp() {
		assertNotAtBoundary(yState, maxY);
		yState++;
		logger.debug("Moved to new Y = {}", yState);
	}
	public void moveDown() {
		assertNotAtBoundary(yState, minY);
		yState--;
		logger.debug("Moved to new Y = {}", yState);
	}
	public void moveLeft() {
		assertNotAtBoundary(xState, minX);
		xState--;
		logger.debug("Moved to new X = {}", xState);
	}
	public void moveRight() {
		assertNotAtBoundary(xState, maxX);
		xState++;
		logger.debug("Moved to new X = {}", xState);
	}
	
	private void assertNotAtBoundary(int current, int boundary) {
		if(current == boundary) {
			throw new UnsupportedOperationException("Current at the boundary, cannot move any further");
		}
	}
	
	@Override
	public State getState() {
		int reward;
		if(isAtGoalState()) {
			reward = 10;
		} else {
			reward = -1;
		}
		
		Set<Action> actions = new HashSet<>();
		
		if(xState > minX) { actions.add(GridWorldAction.LEFT); }
		if(xState < maxX) { actions.add(GridWorldAction.RIGHT); }
		if(yState > minY) { actions.add(GridWorldAction.DOWN); }
		if(yState < maxY) { actions.add(GridWorldAction.UP); }
		
		
		GridWorldState state = new GridWorldState(this.xState, this.yState, reward, actions);
		logger.debug("Current environment: {}", this.toString());
		logger.debug("Returning state: {}", state);
		
		return state;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
			.append("X", xState)
			.append("Y", yState)
			.toString();
	}
}
