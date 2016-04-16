package gridworld.actions;

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


import org.eclipse.jdt.annotation.Nullable;

import qlearning.client.Action;
import gridworld.GridWorldEnvironment;

/* package-private */ abstract class GridWorldAction extends Action {

    protected final GridWorldEnvironment environment;
    protected final int hashCodeValue;

    public GridWorldAction(GridWorldEnvironment environment) {
        this.environment = environment;
        this.hashCodeValue = computeHashCode();
    }

    @Override
    public int hashCode() {
        return hashCodeValue;
    }

    protected int computeHashCode() {
        final int prime = 127;
        int result = 1;
        result = prime * result + environment.hashCode();
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Down other = (Down) obj;
        if (!environment.equals(other.environment))
            return false;
        return true;
    }
}