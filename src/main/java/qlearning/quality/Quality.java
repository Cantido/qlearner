package qlearning.quality;

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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.eclipse.jdt.annotation.Nullable;

import qlearning.domain.Reward;

/**
 * The learned value of a {@link State}-{@link Action} pair's potential for future {@link Reward}s
 */
public class Quality implements Comparable<Quality> {
    public static final Quality ZERO = new Quality(0.0);
    public static final Quality MIN = new Quality(Double.NEGATIVE_INFINITY);
    public static final Quality MAX = new Quality(Double.POSITIVE_INFINITY);
    
    private final double value;
    
    public Quality(double value) {
        if(Double.isNaN(value)) {
            throw new IllegalArgumentException("Cannot create a Quality from NaN");
        }
        this.value = value;
    }
    
    public double toDouble() {
        return value;
    }
    
    @SuppressWarnings("null")
    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public int compareTo(Quality o) {
        return Double.compare(value, o.value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 61).
                append(toDouble()).
                toHashCode();
    }

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
