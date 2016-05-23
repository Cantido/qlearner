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
}
