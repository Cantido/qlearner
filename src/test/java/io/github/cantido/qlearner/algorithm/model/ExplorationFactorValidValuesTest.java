/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with QLearner. If not,
 * see <http://www.gnu.org/licenses/>.
 */

package io.github.cantido.qlearner.algorithm.model;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assume.assumeThat;

import io.github.cantido.qlearner.algorithm.model.ExplorationFactor;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@SuppressWarnings({"null", "javadoc"})
@RunWith(Theories.class)
public class ExplorationFactorValidValuesTest {
  @DataPoints
  public static double[] values = new double[] {Double.NEGATIVE_INFINITY, (-Double.MIN_NORMAL),
      DOUBLE_ZERO, Double.MIN_NORMAL, (DOUBLE_ONE - Double.MIN_NORMAL), DOUBLE_ONE,
      (DOUBLE_ONE + Double.MIN_NORMAL), Double.POSITIVE_INFINITY};

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Theory
  public void explorationFactorBetweenZeroAndOneInclusiveIsLegal(double value) {
    assumeThat(value, greaterThanOrEqualTo(DOUBLE_ZERO));
    assumeThat(value, lessThanOrEqualTo(DOUBLE_ONE));

    new ExplorationFactor(value);

    // Expect no exceptions
  }

  @Theory
  public void explorationFactorBelowZeroIsIllegal(double value) {
    assumeThat(value, lessThan(DOUBLE_ZERO));

    exception.expect(IllegalArgumentException.class);

    new ExplorationFactor(value);
  }

  @Theory
  public void explorationFactorAboveOneIsIllegal(double value) {
    assumeThat(value, greaterThan(DOUBLE_ONE));

    exception.expect(IllegalArgumentException.class);

    new ExplorationFactor(value);
  }
}
