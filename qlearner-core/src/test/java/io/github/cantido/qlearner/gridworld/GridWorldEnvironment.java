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

package io.github.cantido.qlearner.gridworld;

import io.github.cantido.qlearner.agent.Agent;
import io.github.cantido.qlearner.algorithm.model.Reward;
import io.github.cantido.qlearner.client.Action;
import io.github.cantido.qlearner.client.Environment;
import io.github.cantido.qlearner.client.State;
import io.github.cantido.qlearner.gridworld.actions.Down;
import io.github.cantido.qlearner.gridworld.actions.Left;
import io.github.cantido.qlearner.gridworld.actions.Right;
import io.github.cantido.qlearner.gridworld.actions.Up;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * An implementation of Q-Learning that moves an agent through a grid from a starting {@code State}
 * to a goal {@code State}.
 * <p>
 * To drive the {@link Agent} from start state to goal state, each state has a reward value of
 * negative one, except for the goal state, which has a reward of 10. Once we reach the goal state,
 * we reset the agent and let it find its way again. After enough iterations, the {@code Agent}
 * builds up enough quality values to know the correct path to the goal state in a minimal number of
 * moves.
 * </p>
 */

@NotThreadSafe
public class GridWorldEnvironment implements Environment {
  @SuppressWarnings("null")
  @Nonnull
  private static final Logger logger = LoggerFactory.getLogger(GridWorldEnvironment.class);

  private final Point topRightMostPoint;

  private final Point bottomLeftMostPoint = new Point(0, 0);

  private final Point startingPoint;

  private final Point goal;

  @Nonnull private final GridWorldState[][] states;

  private Point currentPoint;
  
  @Nonnull private GridWorldState currentState;

  /** More positive in the Y direction (away from zero). */
  @Nonnull private final Up up = new Up(this);

  /** Less positive in the Y direction (toward zero). */
  @Nonnull private final Down down = new Down(this);

  /** Less positive in the X direction (toward zero). */
  @Nonnull private final Left left = new Left(this);

  /** More positive in the X direction (away from zero). */
  @Nonnull private final Right right = new Right(this);

  /**
   * Build a {@code GridWorldEnvironment} of the specified size, with the specified starting point
   * and goal point.
   * 
   * @param columnsCount horizontal size of the grid, in the interval [1, {@code Integer.MAX_VALUE}]
   * @param rowsCount vertical size of the grid, in the interval [1, {@code Integer.MAX_VALUE}]
   * @param startingColumnIndex horizontal, array-indexed position of the starting state, in the
   *        interval [0, {@code columnsCount}]
   * @param startingRowIndex vertical, array-indexed position of the starting state, in the interval
   *        [0, {@code rowsCount}]
   * @param goalColumnIndex horizontal, array-indexed position of the goal state, in the interval
   *        [0, {@code columnsCount}]
   * @param goalRowIndex vertical, array-indexed position of the goal state, in the interval [0,
   *        {@code rowsCount}]
   */
  public GridWorldEnvironment(
                              @CheckForSigned int columnsCount,
                              @CheckForSigned int rowsCount,
                              @CheckForSigned int startingColumnIndex,
                              @CheckForSigned int startingRowIndex,
                              @CheckForSigned int goalColumnIndex,
                              @CheckForSigned int goalRowIndex) {
    if (columnsCount < 1 || rowsCount < 1) {
      throw new IllegalArgumentException("Column and row sizes cannot be less than 1. "
          + "Got columns count: " + columnsCount + " and rows count " + rowsCount);
    }
    this.topRightMostPoint = new Point(columnsCount - 1, rowsCount - 1);
    
    if (startingColumnIndex < 0 || startingColumnIndex >= columnsCount
        || startingRowIndex < 0 || startingRowIndex >= rowsCount) {
      throw new IllegalArgumentException("Starting indices must be within zero and your provided"
          + " column counts, but starting column index was " + startingColumnIndex
          + " and starting row index was " + startingRowIndex);
    }
    
    this.startingPoint = new Point(startingColumnIndex, startingRowIndex);

    this.currentPoint = startingPoint;
    
    if (goalColumnIndex < 0 || goalColumnIndex >= columnsCount
        || goalRowIndex < 0 || goalRowIndex >= rowsCount) {
      throw new IllegalArgumentException("Goal indices must be within zero and your provided"
          + " column counts, but goal column index was " + goalColumnIndex
          + " and goal row index was " + goalRowIndex);
    }
    
    this.goal = new Point(goalColumnIndex, goalRowIndex);

    states = buildStateCache();

    GridWorldState newCurrentState =
        states[currentPoint.horizontalIndex][currentPoint.verticalIndex];
    if (newCurrentState == null) {
      throw new NullPointerException("State at position ("
          + currentPoint.horizontalIndex + ", " + currentPoint.verticalIndex + ") was null");
    }

    currentState = newCurrentState;
  }

  private GridWorldState[][] buildStateCache() {
    GridWorldState[][] states =
        new GridWorldState[topRightMostPoint.horizontalIndex + 1]
                          [topRightMostPoint.verticalIndex + 1];

    for (int x = bottomLeftMostPoint.horizontalIndex; x <= topRightMostPoint.horizontalIndex; x++) {
      for (int y = bottomLeftMostPoint.verticalIndex; y <= topRightMostPoint.verticalIndex; y++) {
        Point currentPoint = new Point(x, y);
        
        int rewardValue;
        if (isGoalState(x, y)) {
          rewardValue = 10;
        } else {
          rewardValue = -1;
        }

        Set<Action> actions = new HashSet<>(4);

        if (currentPoint.isRightOf(bottomLeftMostPoint)) {
          actions.add(left);
        }
        if (currentPoint.isLeftOf(topRightMostPoint)) {
          actions.add(right);
        }
        if (currentPoint.isAbove(bottomLeftMostPoint)) {
          actions.add(down);
        }
        if (currentPoint.isBelow(topRightMostPoint)) {
          actions.add(up);
        }

        GridWorldState state = new GridWorldState(x, y, new Reward(rewardValue), actions);
        states[x][y] = state;
      }
    }
    return states;
  }
  
  @Override
  public State getState() {
    logger.debug("Current environment: {}", this);

    return currentState;
  }
  
  private GridWorldState getState(
                                  @Nonnegative int horizontalIndex,
                                  @Nonnegative int verticalIndex) {
    GridWorldState state = states[horizontalIndex][verticalIndex];
    if (state == null) {
      throw new NullPointerException("State at position ("
          + horizontalIndex + ", " + verticalIndex + ") was null");
    }
    return state;
  }

  private void setState(@Nonnegative int horizontalIndex, @Nonnegative int verticalIndex) {
    currentPoint = new Point(horizontalIndex, verticalIndex);

    setCurrentState(getState(horizontalIndex, verticalIndex));
  }

  private void setCurrentState(GridWorldState state) {
    currentState = state;
  }
  
  private boolean isGoalState(@Nonnegative int horizontalIndex, @Nonnegative int verticalIndex) {
    return (horizontalIndex == goal.horizontalIndex
        && verticalIndex == goal.verticalIndex);
  }

  /**
   * Check if this environment is at its specified goal state.
   * 
   * @return {@code true} if this environment's current {@link State} is equal to its goal
   *         {@code State}, {@code false} otherwise.
   */
  public boolean isAtGoalState() {
    return currentPoint.equals(goal);
  }

  /**
   * Reset this environment to its goal state.
   */
  public void reset() {
    setState(startingPoint.horizontalIndex, startingPoint.verticalIndex);
  }

  /**
   * Change the state of the environment so that its Y-dimension is more positive.
   * 
   * @throws IllegalStateException if this object's Y-state is at the boundary and cannot be any
   *         higher.
   */
  public void moveUp() {
    assertNotAtBoundary(currentPoint.verticalIndex, topRightMostPoint.verticalIndex);
    setState(currentPoint.horizontalIndex, currentPoint.verticalIndex + 1);
    logger.debug("Moved to new Y = {}", currentPoint.verticalIndex);
  }

  /**
   * Change the state of the environment so that its Y-dimension is less positive, closer to zero.
   * 
   * @throws IllegalStateException if this object's Y-state is at the boundary and cannot be any
   *         lower.
   */
  public void moveDown() {
    assertNotAtBoundary(currentPoint.verticalIndex, bottomLeftMostPoint.verticalIndex);
    setState(currentPoint.horizontalIndex, currentPoint.verticalIndex - 1);
    logger.debug("Moved to new Y = {}", currentPoint.verticalIndex);
  }

  /**
   * Change the state of the environment so that its X-dimension is less positive, closer to zero.
   * 
   * @throws IllegalStateException if this object's X-state is at the boundary and cannot be any
   *         lower.
   */
  public void moveLeft() {
    assertNotAtBoundary(currentPoint.horizontalIndex, bottomLeftMostPoint.horizontalIndex);
    setState(currentPoint.horizontalIndex - 1, currentPoint.verticalIndex);
    logger.debug("Moved to new X = {}", currentPoint.horizontalIndex);
  }

  /**
   * Change the state of the environment so that its X-dimension is more positive.
   * 
   * @throws IllegalStateException if this object's X-state is at the boundary and cannot be any
   *         higher.
   */
  public void moveRight() {
    assertNotAtBoundary(currentPoint.horizontalIndex, topRightMostPoint.horizontalIndex);
    setState(currentPoint.horizontalIndex + 1, currentPoint.verticalIndex);
    logger.debug("Moved to new X = {}", currentPoint.horizontalIndex);
  }

  private void assertNotAtBoundary(@Nonnegative int current, @Nonnegative int boundary) {
    if (current == boundary) {
      throw new IllegalStateException("Current at the boundary, cannot move any further");
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("GridWorldEnvironment[Current state: ").append(currentState).append("]");
    String stringValue = builder.toString();
    if (stringValue == null) {
      throw new NullPointerException("StringBuilder.toString returned null.");
    }
    return stringValue;
  }
}
