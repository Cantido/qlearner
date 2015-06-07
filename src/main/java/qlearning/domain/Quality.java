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

package qlearning.domain;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The learned value of a {@link State}-{@link Action} pair's potential for future {@link Reward}s
 */
public class Quality implements Comparable<Object> {
    private final Double value;
    
    public Quality(Double value) {
        this.value = value;
    }
    
    public Quality(Quality oldQuality,
            LearningRate learningRate,
            Reward reward,
            DiscountFactor discountFactor,
            Quality optimalFutureValueEstimate) {

        Validate.notNull(oldQuality, "Quality cannot be null");
        Validate.notNull(reward, "Reward cannot be null");
        Validate.notNull(optimalFutureValueEstimate, "Optimal future value estimate cannot be null");
        Validate.notNull(learningRate, "LearningRate cannot be null");
        Validate.notNull(discountFactor, "DiscountFactor cannot be null");
        
        value = oldQuality.getValue()
                + (learningRate.getValue() *
                        (reward.getValue() + discountFactor.getValue() * optimalFutureValueEstimate.getValue() - oldQuality.getValue()));
    }
    
    public Double getValue() {
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
          .append(this.getValue(), other.getValue())
          .toComparison();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 61).
                append(getValue()).
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
                      .append(getValue(), rhs.getValue())
                      .isEquals();
    }
    
}
