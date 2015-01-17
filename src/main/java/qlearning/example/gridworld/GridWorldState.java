package qlearning.example.gridworld;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import qlearning.Action;
import qlearning.State;

public class GridWorldState implements State {
    private int x, y, reward;
    private Set<Action> actions = new HashSet<>();

    public GridWorldState(int x, int y, int reward, Set<Action> actions) {
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
    public double getReward() {
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
    public boolean equals(Object obj) {
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
