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

package gridworld;

import java.util.Set;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Signed;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import qlearning.client.Action;
import qlearning.client.State;
import qlearning.domain.learning.Reward;

@Immutable
@ThreadSafe
public class GridWorldState extends State {
    @Nonnegative private final int x, y;
    @Nonnull private final Reward reward;
    @Nonnull private final Set<Action> actions;
    
    @Signed private final int hashCodeValue;
    @Nonnull private final String toStringValue;

    public GridWorldState(@CheckForSigned int x, @CheckForSigned int y, Reward reward, Set<Action> actions) {
    	if(x < 0) throw new IllegalArgumentException("Recieved a negative value for x: got "+x+ ", which is invalid.");
    	if(y < 0) throw new IllegalArgumentException("Recieved a negative value for y: got "+y+ ", which is invalid.");
        this.x = x;
        this.y = y;
        this.reward = reward;
        this.actions = actions;
        
        this.hashCodeValue = computeHashCode();
        this.toStringValue = computeToString();
    }

    @Nonnegative
    public int getX() {
        return x;
    }

    @Nonnegative
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
    
    @Signed
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
    @Signed
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
    
    public String computeToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GridWorldState[")
               .append("X: ").append(x)
               .append(", Y: ").append(y)
               .append(", Reward: ").append(reward)
               .append(", Actions: ").append(actions)
               .append("]");
        String stringValue = builder.toString();
        if(stringValue == null) { throw new NullPointerException("StringBuilder.toString returned null."); }
        return stringValue;
    }
}
