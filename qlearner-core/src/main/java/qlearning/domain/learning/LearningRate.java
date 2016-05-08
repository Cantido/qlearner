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

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import javax.annotation.Nonnegative;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.lang3.Validate;

/**
 * Determines to what extent the newly acquired information will override the old information.
 * <p>
 * A factor of 0 will make the agent not learn anything, while a factor of 1 would make the agent consider only the
 * most recent information. In fully deterministic environments, a learning rate of 1 is optimal. When the problem
 * is stochastic, the algorithm still converges under some technical conditions on the learning rate, that require
 * it to decrease to zero.
 * </p>
 * This value needs to be chosen in correlation with how often a given {@code State-Action} pair will lead to the
 * same next {@code State}. In a grid world, moving UP from one square will always lead to the square above it,
 * so a learning rate of 1 would be correct.
 * <p>
 * The default learning rate is 1.
 * </p>
 */
@Immutable
@ThreadSafe
public final class LearningRate extends Number {
	private static final long serialVersionUID = -1492055404079602330L;
	@Nonnegative private final double value;
    
   /**
    * Create a new {@code LearningRate} with the given value.
    * <p>
    * A factor of 0 will make the agent not learn anything, while a factor of 1 would make the agent consider only the
    * most recent information. In fully deterministic environments, a learning rate of 1 is optimal. When the problem
    * is stochastic, the algorithm still converges under some technical conditions on the learning rate, that require
    * it to decrease to zero.
    * </p>
    * <p>
    * The default learning rate is 1.
    * </p>
    * 
    * @param value the value of the new learning rate, in the range [0, 1]
    */
    public LearningRate(double value) {
        Validate.inclusiveBetween(DOUBLE_ZERO, DOUBLE_ONE, value,
                "Learning rate must be between zero and one (inclusive)");
        this.value = value;
    }

	@Override
	@Nonnegative 
	public int intValue() {
		return (int) value;
	}

	@Override
	@Nonnegative
	public long longValue() {
		return (long) value;
	}

	@Override
	@Nonnegative
	public float floatValue() {
		return (float) value;
	}

	@Override
	@Nonnegative
	public double doubleValue() {
		return value;
	}

    @Override
    public String toString() {
        return "LearningRate["+value+"]";
    }
}
