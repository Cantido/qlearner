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

import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;

import qlearning.Action;
import qlearning.State;
import qlearning.domain.Reward;

public class GridWorldState implements State {
    private final int x, y;
    private final Reward reward;
    private final Set<Action> actions;
    
    private final int hashCodeValue;
    private final String toStringValue;

    public GridWorldState(int x, int y, Reward reward, Set<Action> actions) {
        this.x = x;
        this.y = y;
        this.reward = reward;
        this.actions = actions;
        
        this.hashCodeValue = computeHashCode();
        this.toStringValue = computeToString();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public Reward getReward() {
        return this.reward;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    private int computeHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (actions.hashCode());
        result = prime * result + (reward.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    
    @Override
    public int hashCode() {
        return this.hashCodeValue;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GridWorldState other = (GridWorldState) obj;
        if (!actions.equals(other.actions))
            return false;
        if (!reward.equals(other.reward))
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return toStringValue;
    }
    
    @SuppressWarnings("null")
    public String computeToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GridWorldState[")
               .append("X: ").append(x)
               .append(", Y: ").append(y)
               .append(", Reward: ").append(reward)
               .append(", Actions: ").append(actions)
               .append("]");
        return builder.toString();
    }
}
