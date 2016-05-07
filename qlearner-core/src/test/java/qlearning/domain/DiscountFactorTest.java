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

package qlearning.domain;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assume.assumeThat;

import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import qlearning.domain.learning.DiscountFactor;


@RunWith(Theories.class)
public class DiscountFactorTest {

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
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Theory
    public void discountFactorBelowZeroIsIllegal(double discountFactor) {
        assumeThat(discountFactor, lessThan(DOUBLE_ZERO));

        exception.expect(IllegalArgumentException.class);
        new DiscountFactor(discountFactor);
    }

    @Theory
    public void discountFactorAboveZeroIsLegal(double discountFactor) {
        assumeThat(discountFactor, greaterThan(DOUBLE_ZERO));

        new DiscountFactor(discountFactor);

        // Expect no exceptions
    }
}