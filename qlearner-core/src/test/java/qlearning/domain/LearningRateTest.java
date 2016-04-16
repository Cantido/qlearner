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
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assume.assumeThat;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import qlearning.domain.learning.LearningRate;

@NonNullByDefault({})
@RunWith(Theories.class)
public class LearningRateTest {
    @Rule public ExpectedException exception = ExpectedException.none();

    @DataPoints
    public static double[] doubles = new double[] {
        Double.NEGATIVE_INFINITY,
        (- Double.MIN_NORMAL),
        DOUBLE_ZERO,
        Double.MIN_NORMAL,
        (DOUBLE_ONE - Double.MIN_NORMAL),
        DOUBLE_ONE,
        (DOUBLE_ONE + Double.MIN_NORMAL),
        Double.POSITIVE_INFINITY
    };
    
    @SuppressWarnings("unused")
    @Theory
    public void learningRateBetweenZeroAndOneInclusiveIsLegal(double learningRate) {
        assumeThat(learningRate, greaterThanOrEqualTo(DOUBLE_ZERO));
        assumeThat(learningRate, lessThanOrEqualTo(DOUBLE_ONE));
        
        // No exception expected
        new LearningRate(learningRate);
    }

    @SuppressWarnings("unused")
    @Theory
    public void learningRateLessThanZeroIsIllegal(double learningRate) {
        assumeThat(learningRate, lessThan(DOUBLE_ZERO));

        exception.expect(IllegalArgumentException.class);
        new LearningRate(learningRate);
    }

    @SuppressWarnings("unused")
    @Theory
    public void learningRateGreaterThanOneIsIllegal(double learningRate) {
        assumeThat(learningRate, greaterThan(DOUBLE_ONE));

        exception.expect(IllegalArgumentException.class);
        new LearningRate(learningRate);
    }

}
