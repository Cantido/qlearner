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

import java.util.Comparator;
import java.util.PriorityQueue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Signed;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import qlearning.client.Action;
import qlearning.client.State;
import qlearning.domain.learning.Reward;

/**
 * The learned value of a {@link State}-{@link Action} pair's potential for future {@link Reward}s
 */
@Immutable
@ThreadSafe
public final class Quality extends Number implements Comparable<Quality> {
	private static final long serialVersionUID = -6538970599397750791L;

    /**
     * A {@link Comparator} sorts {@link Quality} objects in reverse order
     * from their natural ordering.
     * 
     * When used in a {@link PriorityQueue}, then the highest quality
     * will be at the top of the queue.
     */
	@Nonnull public static final Comparator<Quality> DESCENDING_ORDER = new ReverseOrder();
    
    @Nonnull public static final Quality ZERO = new Quality(0.0);
    @Nonnull public static final Quality MIN = new Quality(Double.NEGATIVE_INFINITY);
    @Nonnull public static final Quality MAX = new Quality(Double.POSITIVE_INFINITY);
    
    @Signed private final double value;
    @Signed private final int hashCode;
    
    public Quality(@Signed double value) {
        if(Double.isNaN(value)) {
            throw new IllegalArgumentException("Cannot create a Quality from NaN");
        }
        this.value = value;
        this.hashCode = calculateHashCode(value);
    }

    @Override
    @Signed public int intValue() {
        return Double.valueOf(value).intValue();
    }

    @Override
    @Signed public long longValue() {
        return Double.valueOf(value).longValue();
    }

    @Override
    @Signed public float floatValue() {
        return Double.valueOf(value).floatValue();
    }

    @Override
    @Signed public double doubleValue() {
        return value;
    }
    
	@Override
    public String toString() {
		String stringValue = Double.toString(value);
		if(stringValue == null) throw new AssertionError("Double.toString returned null");
        return stringValue;
    }

    @Override
    @SuppressFBWarnings(
    		value = "NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE",
    		justification = "We are overriding compareTo, which is defined " +
    						"as @Nullable. This is a false positive.")
    @Signed public int compareTo(@Nullable Quality o) {
    	if(o == null) { throw new NullPointerException("Tried to compare Quality to null value."); }
        return Double.compare(value, o.value);
    }

    @Override
    @Signed public int hashCode() {
        return hashCode;
    }

    @Signed private int calculateHashCode(double value) {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }

    @SuppressFBWarnings(
    		value = "NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION",
    		justification = "We are overriding equals, which is defined " +
    						"as @Nullable. This is a false positive.")
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        Quality rhs = (Quality) obj;
        return value == rhs.value;
    }
}
