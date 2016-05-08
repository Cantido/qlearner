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

package gridworld;/*
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

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.client.Action;
import qlearning.client.Environment;
import qlearning.client.State;
import qlearning.agent.Agent;
import qlearning.domain.learning.Reward;
import gridworld.actions.Down;
import gridworld.actions.Left;
import gridworld.actions.Right;
import gridworld.actions.Up;

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

@NotThreadSafe
public class GridWorldEnvironment implements Environment {
    @SuppressWarnings("null")
	@Nonnull private static final Logger logger = LoggerFactory.getLogger(GridWorldEnvironment.class);
    
    @Nonnegative private final int maxX;
    @Nonnegative private final int maxY;

    @Nonnegative private final int minX = 0;
    @Nonnegative private final int minY = 0;

    @Nonnegative private final int startX;
    @Nonnegative private final int startY;

    @Nonnegative private final int goalX;
    @Nonnegative private final int goalY;

    @Nonnull private final GridWorldState [][] states;
    
    @Nonnegative private int xState;
    @Nonnegative private int yState;
    @Nonnull private GridWorldState currentState;
    
    /**
     * More positive in the Y direction (away from zero)
     */
    @Nonnull private final Up UP = new Up(this);
    
    /**
     * Less positive in the Y direction (toward zero)
     */
    @Nonnull private final Down DOWN = new Down(this);
    
    /**
     * Less positive in the X direction (toward zero)
     */
    @Nonnull private final Left LEFT = new Left(this);
    
    /**
     * More positive in the X direction (away from zero)
     */
    @Nonnull private final Right RIGHT = new Right(this);

    public GridWorldEnvironment(
        @Nonnegative @CheckForSigned int sizeX,
        @Nonnegative @CheckForSigned int sizeY,
        @Nonnegative @CheckForSigned int startX,
        @Nonnegative @CheckForSigned int startY,
        @Nonnegative @CheckForSigned int goalX,
        @Nonnegative @CheckForSigned int goalY) {
        this.maxX = sizeX;
        this.maxY = sizeY;
        
        this.startX = startX;
        this.startY = startY;
        
        this.xState = startX;
        this.yState = startY;
        
        this.goalX = goalX;
        this.goalY = goalY;
        
        states = buildStateCache();
        
        GridWorldState newCurrentState = states[xState][yState];
        if(newCurrentState == null) {
            throw new NullPointerException("State at position (" + xState + ", " + yState + ") was null");
        }
        
        currentState = newCurrentState;
    }

    private GridWorldState [][] buildStateCache() {
        GridWorldState [][] states = new GridWorldState [maxX+1][maxY+1];
        
        for(int x = minX; x < maxX+1; x++) {
            for(int y = minY; y < maxY+1; y++) {
                int rewardValue;
                if (isGoalState(x, y)) {
                    rewardValue = 10;
                } else {
                    rewardValue = -1;
                }

                Set<Action> actions = new HashSet<>(4);

                if (x > minX) { actions.add(LEFT); }
                if (x < maxX) { actions.add(RIGHT); }
                if (y > minY) { actions.add(DOWN); }
                if (y < maxY) {  actions.add(UP); }

                GridWorldState state = new GridWorldState(x, y, new Reward(rewardValue), actions);
                states[x][y] = state;
            }
        }
        return states;
    }
    
    private GridWorldState getState(@Nonnegative int x, @Nonnegative int y) {
        GridWorldState state = states[x][y];
        if(state == null){
            throw new NullPointerException("State at position (" + x + ", " + y + ") was null");
        }
        return state;
    }
    
    private void setState(@Nonnegative int x, @Nonnegative int y) {
        xState = x;
        yState = y;
        
        setCurrentState(getState(xState, yState));
    }
    
    private void setCurrentState(GridWorldState state) {
        currentState = state;
    }
    
    private boolean isGoalState(@Nonnegative int x, @Nonnegative int y) {
        return (x == goalX && y == goalY);
    }

    public boolean isAtGoalState() {
        return (xState == goalX && yState == goalY);
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

    private void assertNotAtBoundary(@Nonnegative int current, @Nonnegative int boundary) {
        if (current == boundary) {
            throw new UnsupportedOperationException("Current at the boundary, cannot move any further");
        }
    }
    
    @Override
    public State getState() {
        logger.debug("Current environment: {}", this);
        
        return currentState;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GridWorldEnvironment[Current state: ")
               .append(currentState)
               .append("]");
        String stringValue = builder.toString();
        if(stringValue == null) { throw new NullPointerException("StringBuilder.toString returned null."); }
        return stringValue;
    }
}
