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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.jdt.annotation.Nullable;

import qlearning.Action;
import qlearning.State;
import qlearning.domain.Reward;

public class GridWorldState implements State {
    private int x, y;
    private Reward reward;
    private Set<Action> actions = new HashSet<>();

    public GridWorldState(int x, int y, Reward reward, Set<Action> actions) {
        this.x = x;
        this.y = y;
        this.reward = reward;
        this.actions = actions;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder(107, 29)
            .append(x)
            .append(y)
            .toHashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        
        GridWorldState rhs = (GridWorldState) obj;
        return new EqualsBuilder()
            .append(x, rhs.getX())
            .append(y, rhs.getY())
            .isEquals();
    }

    @SuppressWarnings("null")
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("X", x)
            .append("Y", y)
            .append("reward", reward)
            .append("actions", actions)
            .toString();
    }
}
