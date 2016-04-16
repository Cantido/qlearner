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

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import org.apache.commons.lang3.Validate;

public class ExplorationFactor extends Number {
    private final Double value;
    
    public ExplorationFactor(double value) {
        validateFactorValue(value);
        this.value = value;
    }
    
    private void validateFactorValue(Double factorValue) {
    	Validate.notNull(factorValue, "Exploration factor must not be null");
        Validate.inclusiveBetween(DOUBLE_ZERO, DOUBLE_ONE, factorValue, "Exploration factor must be within [0,1]");
    }
    
    public boolean shouldExplore(Double checkValue) {
        validateCheckValue(checkValue);
        if (value.equals(DOUBLE_ZERO)) return false;
        if (value.equals(DOUBLE_ONE)) return true;
        return checkValue < value;
    }
    
    private void validateCheckValue(Double checkValue) {
    	Validate.notNull(checkValue, "Test value must not be null");
        Validate.isTrue(!DOUBLE_ONE.equals(checkValue), "Test value must be within [0,1). Given value: %f", checkValue);
        Validate.inclusiveBetween(DOUBLE_ZERO, DOUBLE_ONE, checkValue, "Test value must be within [0,1). Given value: %f", checkValue);
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
        return String.format("ExplorationFactor[%f]", value);
    }

}
