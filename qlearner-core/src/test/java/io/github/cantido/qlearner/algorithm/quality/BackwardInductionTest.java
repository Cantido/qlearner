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
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

@SuppressWarnings({"null", "javadoc"})
public class BackwardInductionTest {
  @Rule
  public MockitoRule mockito = MockitoJUnit.rule();

  private final QualityUpdateStrategy backwardInduction = new BackwardInduction();

  @Test
  public void computeNewQuality() {
    Quality oldQuality = new Quality(1.0);
    LearningRate learningRate = new LearningRate(1.0);
    Reward reward = new Reward(1.0);
    DiscountFactor discountFactor = new DiscountFactor(1);
    Quality optimalFutureValueEstimate = new Quality(1.0);

    Quality expected = new Quality(2.0);
    Quality actual = backwardInduction.next(oldQuality, learningRate, reward, discountFactor,
        optimalFutureValueEstimate);

    assertThat(actual, is(expected));
  }
}
