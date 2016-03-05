package qlearning.example.gridworld;

/*
 * #%L
 * QLearner
 * %%
 * Copyright (C) 2012 - 2016 Robert Richter
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.Action;
import qlearning.Environment;
import qlearning.State;
import qlearning.agent.Agent;
import qlearning.domain.Reward;

/**
 * An implementation of Q-Learning that moves an agent through a grid from a starting {@code State} to a goal
 * {@code State}.
 * <p>
 * To drive the {@link Agent} from start state to goal state, each state has a reward value of negative one, except for
 * the goal state, which has a reward of 10. Once we reach the goal state, we reset the agent and let it find its way
 * again. After enough iterations, the {@code Agent} builds up enough quality values to know the correct path to the
 * goal state in a minimal number of moves.
 * </p>
 */
public class GridWorldEnvironment implements Environment {
    @SuppressWarnings("null")
    private Logger logger = LoggerFactory.getLogger(getClass());

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
    
    GridWorldState currentState;
    GridWorldState [][] states = {};

    public GridWorldEnvironment() {
        GridWorldAction.setGridWorld(this);
        buildStateCache();
        
        // Redundant, but Eclipse won't yell at us about a null current state anymore
        GridWorldState newCurrentState = states[xState][yState];
        if(newCurrentState == null) {
            throw new NullPointerException("State at position (" + xState + ", " + yState + ") was null");
        }
        
        currentState = newCurrentState;
    }

    public void setSize(int totalX, int totalY) {
        this.maxX = totalX;
        this.maxY = totalY;
        buildStateCache();
    }
    
    private void buildStateCache() {
        GridWorldState [][] states = new GridWorldState [maxX+1][maxY+1];
        
        for(int x = minX; x < maxX+1; x++) {
            for(int y = minY; y < maxY+1; y++) {
                int rewardValue;
                if (isGoalState(x, y)) {
                    rewardValue = 10;
                } else {
                    rewardValue = -1;
                }

                Set<Action> actions = new HashSet<>();

                if (x > minX) { actions.add(GridWorldAction.LEFT); }
                if (x < maxX) { actions.add(GridWorldAction.RIGHT); }
                if (y > minY) { actions.add(GridWorldAction.DOWN); }
                if (y < maxY) {  actions.add(GridWorldAction.UP); }

                GridWorldState state = new GridWorldState(x, y, new Reward(rewardValue), actions);
                states[x][y] = state;
            }
        }
        setStates(states);
    }
    
    public void setStart(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    public void setGoal(int x, int y) {
        this.goalX = x;
        this.goalY = y;
    }
    
    private GridWorldState getState(int x, int y) {
        GridWorldState state = states[x][y];
        if(state == null){
            throw new NullPointerException("State at position (" + x + ", " + y + ") was null");
        }
        return state;
    }
    
    private void setState(int x, int y) {
        xState = x;
        yState = y;
        
        setCurrentState(getState(xState, yState));
    }
    
    private void setCurrentState(GridWorldState state) {
        currentState = state;
    }
    
    private void setStates(GridWorldState [][] states) {
        this.states = states;
        setCurrentState(getState(xState, yState));
    }
    
    private boolean isGoalState(int x, int y) {
        return (x == goalX && y == goalY);
    }

    public boolean isAtGoalState() {
        return isGoalState(xState, yState);
    }

    public void reset() {
        setState(startX, startY);
    }

    public void moveUp() {
        assertNotAtBoundary(yState, maxY);
        setState(xState, yState + 1);
        logger.debug("Moved to new Y = {}", yState);
    }

    public void moveDown() {
        assertNotAtBoundary(yState, minY);
        setState(xState, yState - 1);
        logger.debug("Moved to new Y = {}", yState);
    }

    public void moveLeft() {
        assertNotAtBoundary(xState, minX);
        setState(xState - 1, yState);
        logger.debug("Moved to new X = {}", xState);
    }

    public void moveRight() {
        assertNotAtBoundary(xState, maxX);
        setState(xState + 1, yState);
        logger.debug("Moved to new X = {}", xState);
    }

    private void assertNotAtBoundary(int current, int boundary) {
        if (current == boundary) {
            throw new UnsupportedOperationException("Current at the boundary, cannot move any further");
        }
    }
    

    @Override
    public State getState() {
        logger.debug("Current environment: {}", this.toString());
        logger.debug("Returning state: {}", currentState);
        
        return currentState;
    }

    @Override
    public String toString() {
        return "GridWorldEnvironment[Current state: " + currentState + "]";
    }
}
