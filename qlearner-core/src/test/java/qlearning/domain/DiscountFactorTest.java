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

package qlearning.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import qlearning.domain.learning.DiscountFactor;

@SuppressWarnings({"null", "javadoc"})
@RunWith(JUnitParamsRunner.class)
public class DiscountFactorTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @SuppressWarnings("unused")
  private Object[] validValues() {
    return new Object[] {
        0.0,
        Double.MIN_NORMAL,
        1.0 - Double.MIN_NORMAL,
        1.0,
        1.0 + Double.MIN_NORMAL,
        Double.POSITIVE_INFINITY
    };
  }
  
  @Test
  @Parameters(method = "validValues")
  public void acceptsValidValues(Double validValue) throws Exception {
    new DiscountFactor(validValue);
  }
  
  @SuppressWarnings("unused")
  private Object[] invalidValues() {
    return new Object[] {
        Double.NEGATIVE_INFINITY,
        -Double.MIN_NORMAL
    };
  }

  @Test
  @Parameters(method = "invalidValues")
  public void invalidValuesThrowRuntimeException(Double invalidValue) {
    exception.expect(IllegalArgumentException.class);
    new DiscountFactor(invalidValue);
  }

}
