package qlearning.domain;

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

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import org.apache.commons.lang3.Validate;

/**
 * The discount factor determines the importance of future rewards.
 * <p>
 * A factor of 0 will make the agent "myopic" (or short-sighted) by only considering current rewards, while a factor
 * approaching 1 will make it strive for a long-term high reward. If the discount factor meets or exceeds 1, the
 * quality values may diverge.
 * </p>
 */
public class DiscountFactor extends Number {
    private final Double value;
    
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
        Validate.inclusiveBetween(DOUBLE_ZERO, Double.POSITIVE_INFINITY, value,
                "Discount factor must be greater than or equal to zero");
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

    @SuppressWarnings("null")
    @Override
    public String toString() {
        return String.format("DiscountFactor[%f]", value);
    }
}
