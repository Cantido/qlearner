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

package qlearning.domain.quality;

import org.eclipse.jdt.annotation.Nullable;

import qlearning.client.Action;
import qlearning.client.State;

/**
 * A triplet of a {@link State}, {@link Action}, and {@link Quality} of that pair
 */
public class StateActionQuality implements Comparable<StateActionQuality> {
    private final State state;
    private final Action action;
    private final Quality quality;
    
    public StateActionQuality(State state, Action action, Quality quality) {
        this.state = state;
        this.action = action;
        this.quality = quality;
    }
    
    public State getState() {
        return this.state;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public Quality getQuality() {
        return this.quality;
    }
    
    @Override
    public String toString() {
        return "StateActionQuality [state=" + state + ", action=" + action + ", quality=" + quality + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + action.hashCode();
        result = prime * result + quality.hashCode();
        result = prime * result + state.hashCode();
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
        StateActionQuality other = (StateActionQuality) obj;
        if (!action.equals(other.action))
            return false;
        if (!quality.equals(other.quality))
            return false;
        if (!state.equals(other.state))
            return false;
        return true;
    }

    @Override
    public int compareTo(StateActionQuality o) {
        return quality.compareTo(o.quality);
    }
}
