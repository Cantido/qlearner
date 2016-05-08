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

package qlearning.domain.exploration;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Signed;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Represent's an agent's likelihood of taking non-optimal actions in order to
 * explore the problem space.
 * 
 * <p>
 * A value of 0.0 means that the agent should never explore, and a value of 1.0
 * means that the agent will always explore. How exactly your agent explores is
 * decided by your {@link ExplorationStrategy}.
 * </p>
 */
@Immutable
@ThreadSafe
public final class ExplorationFactor extends Number {
	@Signed
	private static final long serialVersionUID = -4745713414722996934L;
	
	private static final double ALWAYS_EXPLORE_VALUE = 1.0;
	private static final double NEVER_EXPLORE_VALUE = 0.0;
	
	/**
	 * An {@code ExplorationFactor} whose {@link #shouldExplore(Number)} will
	 * always return {@code true}.
	 */
	public static final ExplorationFactor ALWAYS_EXPLORE = new ExplorationFactor(ALWAYS_EXPLORE_VALUE);
	
	/**
	 * An {@code ExplorationFactor} whose {@link #shouldExplore(Number)} will
	 * always return {@code true}.
	 */
	public static final ExplorationFactor NEVER_EXPLORE = new ExplorationFactor(NEVER_EXPLORE_VALUE);

	@Nonnegative
	private final double value;

	/**
	 * Create an exploration factor of the given value.
	 * 
	 * @param value
	 *            the value of this factor in the interval [0,1]. A value of 0.0
	 *            means that the agent should never explore, and a value of 1.0
	 *            means that the agent will always explore.
	 */
	public ExplorationFactor(@Nonnegative @CheckForSigned Number value) {
		@CheckForSigned
		@Nonnegative
		double doubleValue = value.doubleValue();

		if (doubleValue < 0.0 || doubleValue > 1.0) {
			throw new IllegalArgumentException(
					"Exploration Factor value must be within [0,1]. Given value: " + doubleValue);
		}

		this.value = doubleValue;
	}

	/**
	 * Test a value against this factor to see if exploration should happen.
	 * 
	 * @param checkValue
	 *            the value to compare against this factor in the interval
	 *            [0,1).
	 * @return {@code true} if exploration should take place, {@code false}
	 *         otherwise.
	 */
	public boolean shouldExplore(@Nonnegative @CheckForSigned Number checkValue) {
		@CheckForSigned
		@Nonnegative
		double doubleCheckValue = checkValue.doubleValue();

		if (doubleCheckValue < 0.0 || doubleCheckValue >= 1.0) {
			throw new IllegalArgumentException("Test value must be within [0,1). Given value: " + checkValue);
		}
		if (value == NEVER_EXPLORE_VALUE)
			return false;
		if (value == ALWAYS_EXPLORE_VALUE)
			return true;
		return doubleCheckValue < value;
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
		return "ExplorationFactor[" + value + "]";
	}
}
