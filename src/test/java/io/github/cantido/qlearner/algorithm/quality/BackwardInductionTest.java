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

package io.github.cantido.qlearner.algorithm.quality;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import io.github.cantido.qlearner.algorithm.model.DiscountFactor;
import io.github.cantido.qlearner.algorithm.model.LearningRate;
import io.github.cantido.qlearner.algorithm.model.Quality;
import io.github.cantido.qlearner.algorithm.model.QualityUpdateStrategy;
import io.github.cantido.qlearner.algorithm.model.Reward;
import io.github.cantido.qlearner.algorithm.quality.BackwardInduction;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

@SuppressWarnings({"null", "javadoc"})
@RunWith(JUnitParamsRunner.class)
public class BackwardInductionTest {
  @Rule
  public MockitoRule mockito = MockitoJUnit.rule();

  private final QualityUpdateStrategy backwardInduction = new BackwardInduction();
  
  /*
   * Using a lot of primes to improve my chances of getting unique values.
   * 
   * Each one of these values differs from the first by only one variable.
   */
  @SuppressWarnings("unused")
  private Object[] newQualityValues() {
    return new Object[] {
      new Object[] {
        new Quality(3),
        new LearningRate(0.8),
        new Reward(7),
        new DiscountFactor(0.4),
        new Quality(11),
        new Quality(9.72)
      },
      new Object[] {
        new Quality(13),          // Only difference from first
        new LearningRate(0.8),
        new Reward(7),
        new DiscountFactor(0.4),
        new Quality(11),
        new Quality(11.72)
      },
      new Object[] {
        new Quality(3),
        new LearningRate(0.3),    // Only difference from first
        new Reward(7),
        new DiscountFactor(0.4),
        new Quality(11),
        new Quality(5.52)
      },
      new Object[] {
        new Quality(3),
        new LearningRate(0.8),
        new Reward(17),           // Only difference from first
        new DiscountFactor(0.4),
        new Quality(11),
        new Quality(17.72)
      },
      new Object[] {
        new Quality(3),
        new LearningRate(0.8),
        new Reward(7),
        new DiscountFactor(0.1),  // Only difference from first
        new Quality(11),
        new Quality(7.08)
      },
      new Object[] {
        new Quality(3),
        new LearningRate(0.8),
        new Reward(7),
        new DiscountFactor(0.4),
        new Quality(13),          // Only difference from first
        new Quality(10.36)
      },
    };
  }
  
  @Test
  @Parameters(method = "newQualityValues")
  public void computeNewQuality(
                                Quality oldQuality,
                                LearningRate learningRate, 
                                Reward reward,
                                DiscountFactor discountFactor,
                                Quality optimalFutureValueEstimate,
                                Quality expected) {
    Quality actual = backwardInduction.next(
                                            oldQuality,
                                            learningRate,
                                            reward,
                                            discountFactor,
                                            optimalFutureValueEstimate);

    assertThat(actual, is(expected));
  }
}
