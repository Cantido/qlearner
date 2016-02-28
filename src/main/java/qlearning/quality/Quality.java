/**
 * This file is part of qlearner
 *
 *  Qlearner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Qlearner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Qlearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.quality;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import qlearning.domain.Reward;

/**
 * The learned value of a {@link State}-{@link Action} pair's potential for future {@link Reward}s
 */
public class Quality implements Comparable<Object> {
    private final Double value;
    
    public Quality(Double value) {
        this.value = value;
    }
    
    public Double toDouble() {
        return value;
    }
    
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(Object o) {
        Quality other = (Quality) o;
        return new CompareToBuilder()
          .append(this.toDouble(), other.toDouble())
          .toComparison();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 61).
                append(toDouble()).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        Quality rhs = (Quality) obj;
        return new EqualsBuilder()
                      .append(toDouble(), rhs.toDouble())
                      .isEquals();
    }
    
}
