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

import java.util.concurrent.locks.ReentrantReadWriteLock;

import qlearning.client.State;
import qlearning.agent.Agent;

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
public class ConcurrentGridWorldEnvironment extends GridWorldEnvironment {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ConcurrentGridWorldEnvironment(int sizeX, int sizeY, int startX, int startY, int goalX, int goalY) {
        super(sizeX, sizeY, startX, startY, goalX, goalY);
    }

    @Override
    public boolean isAtGoalState() {
        lock.readLock().lock();
        boolean isAtGoalState = super.isAtGoalState();
        lock.readLock().unlock();
        return isAtGoalState;
    }

    @Override
    public void reset() {
        lock.writeLock().lock();
        super.reset();
        lock.writeLock().unlock();
    }

    @Override
    public void moveUp() {
        lock.writeLock().lock();
        super.moveUp();
        lock.writeLock().unlock();
    }

    @Override
    public void moveDown() {
        lock.writeLock().lock();
        super.moveDown();
        lock.writeLock().unlock();
    }

    @Override
    public void moveLeft() {
        lock.writeLock().lock();
        super.moveLeft();
        lock.writeLock().unlock();
    }

    @Override
    public void moveRight() {
        lock.writeLock().lock();
        super.moveRight();
        lock.writeLock().unlock();
    }

    @Override
    public State getState() {
        lock.readLock().lock();
        State state = super.getState();
        lock.readLock().unlock();
        return state;
    }

    @Override
    public String toString() {
        lock.readLock().lock();
        String string = super.toString() + "(** Concurrent version **)";
        lock.readLock().unlock();
        return string;
    }
}
