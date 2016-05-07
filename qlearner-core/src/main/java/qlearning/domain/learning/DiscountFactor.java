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

package qlearning.domain.learning;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * The discount factor determines the importance of future rewards.
 * <p>
 * A factor of 0 will make the agent "myopic" (or short-sighted) by only considering current rewards, while a factor
 * approaching 1 will make it strive for a long-term high reward. If the discount factor meets or exceeds 1, the
 * quality values may diverge.
 * </p>
 */
public class DiscountFactor extends Number {
	private static final long serialVersionUID = 743132734230321555L;
	
	@Nonnull @Nonnegative private final Double value;
	
    /**
     * Create a new {@code DiscountFactor} with the given value
     * <p>
     * A factor of 0 will make the agent "myopic" (or short-sighted) by only considering current rewards, while a factor
     * approaching 1 will make it strive for a long-term high reward. If the discount factor meets or exceeds 1, the
     * quality values may diverge.
     * </p>
     * 
     * @param value the value of the new discount factor, in the range [0, &infin;)
     */
    public DiscountFactor(double value) {
        if(value < 0.0) throw new IllegalArgumentException("Discount factor must be greater than or equal to zero");

        this.value = value;
    }
    
    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DiscountFactor["+value+"]";
    }
}
