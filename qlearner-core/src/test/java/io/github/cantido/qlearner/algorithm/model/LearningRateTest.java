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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.testing.EqualsTester;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@SuppressWarnings({"null", "javadoc"})
@RunWith(JUnitParamsRunner.class)
public class LearningRateTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  @Parameters({"-0", "0", "0.1", "0.5", "0.9", "1"})
  public void learningRateBetweenZeroAndOneInclusiveIsLegal(double learningRate) {
    // No exception expected
    new LearningRate(learningRate);
  }

  @Test
  @Parameters({"-0.1", "-1"})
  public void learningRateLessThanZeroIsIllegal(double learningRate) {
    exception.expect(IllegalArgumentException.class);
    new LearningRate(learningRate);
  }

  @Test
  @Parameters({"1.1", "2.0"})
  public void learningRateGreaterThanOneIsIllegal(double learningRate) {
    exception.expect(IllegalArgumentException.class);
    new LearningRate(learningRate);
  }
  
  @Test
  @Parameters({"-0", "0", "0.1", "0.5", "0.9", "1"})
  public void convertedNumberValuesAreCorrect(double learningRate) {
    LearningRate rate = new LearningRate(learningRate);
    
    assertThat(rate.intValue(), is(Double.valueOf(learningRate).intValue()));
    assertThat(rate.longValue(), is(Double.valueOf(learningRate).longValue()));
    assertThat(rate.floatValue(), is(Double.valueOf(learningRate).floatValue()));
    assertThat(rate.doubleValue(), is(Double.valueOf(learningRate).doubleValue()));
  }
  
  @Test
  @Parameters({"-0", "0", "0.1", "0.5", "0.9", "1"})
  public void toStringContainsValue(double learningRate) {
    LearningRate rate = new LearningRate(learningRate);
    assertThat(rate, hasToString(containsString(Double.toString(learningRate))));
  }
  
  @Test
  public void equalsTest() throws Exception {
    new EqualsTester()
          .addEqualityGroup(
              new LearningRate(1),
              new LearningRate(1.0),
              new LearningRate(1L),
              new LearningRate((float) 1.0))
          .addEqualityGroup(
              new LearningRate(0.5),
              new LearningRate((float) 0.5))
          .addEqualityGroup(
              new LearningRate(0),
              new LearningRate(0.0),
              new LearningRate((float) 0.0),
              new LearningRate(0L))
          .testEquals();
  }
}
