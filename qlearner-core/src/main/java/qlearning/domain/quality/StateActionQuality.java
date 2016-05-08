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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Signed;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import qlearning.client.Action;
import qlearning.client.State;

/**
 * A triplet of a {@link State}, {@link Action}, and {@link Quality} of that pair
 */
@Immutable
@ThreadSafe
public class StateActionQuality implements Comparable<StateActionQuality> {
    @Nonnull private final State state;
    @Nonnull private final Action action;
    @Nonnull private final Quality quality;
    
    /**
     * Create a {@code StateActionQuality} triplet with the given {@code Quality}
     * value.
     * 
     * @param state
     * @param action
     * @param quality
     */
    public StateActionQuality(State state, Action action, Quality quality) {
        this.state = state;
        this.action = action;
        this.quality = quality;
    }
    
    /**
     * Get the state stored in this triplet.
     * 
     * @return the state of this triplet.
     */
    public State getState() {
        return this.state;
    }
    
    /**
     * Get the action stored in this triplet.
     * 
     * @return the action of this triplet.
     */
    public Action getAction() {
        return this.action;
    }
    
    /**
     * Get the quality stored in this triplet.
     * 
     * @return the quality of this triplet.
     */
    public Quality getQuality() {
        return this.quality;
    }
    
    @Override
    public String toString() {
        return "StateActionQuality [state=" + state + ", action=" + action + ", quality=" + quality + "]";
    }

    @Override
    @Signed
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + action.hashCode();
        result = prime * result + quality.hashCode();
        result = prime * result + state.hashCode();
        return result;
    }
    
    @SuppressFBWarnings(
    		value = "NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION",
    		justification = "We are overriding equals, which is defined " +
    						"as @Nullable. This is a false positive.")
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StateActionQuality other = (StateActionQuality) obj;
        if (!action.equals(other.action)) return false;
        if (!quality.equals(other.quality)) return false;
        return state.equals(other.state);
    }

    @SuppressFBWarnings(
    		value = "NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE",
    		justification = "We are overriding compareTo, which is defined " +
    						"as @Nullable. This is a false positive.")
    @Override
    @Signed
    public int compareTo(@Nullable StateActionQuality o) {
    	if(o == null) throw new NullPointerException("Tried to compare this value to null.");
        return quality.compareTo(o.quality);
    }
}
