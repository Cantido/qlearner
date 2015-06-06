package qlearning.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import qlearning.Action;
import qlearning.State;

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
    public int hashCode() {
        return new HashCodeBuilder(47, 61)
                .append(state)
                .append(action)
                .append(quality)
                .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        StateActionQuality rhs = (StateActionQuality) obj;
        return new EqualsBuilder()
                      .append(quality, rhs.getQuality())
                      .append(state, rhs.getState())
                      .append(action, rhs.getAction())
                      .isEquals();
    }
    
    @Override
    public int compareTo(StateActionQuality o) {
        return quality.compareTo(o.getQuality());
    }
    
    
}
