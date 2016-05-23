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

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;

import com.google.common.testing.EqualsTester;
import io.github.cantido.qlearner.algorithm.model.Quality;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings({"null", "javadoc"})
@RunWith(JUnitParamsRunner.class)
public class QualityTest {

  @SuppressWarnings("unused")
  private Object[] compareToParams() {
    return new Object[] {
        new Object[] {new Quality(1),new Quality(0)},
        new Object[] {new Quality(0),new Quality(-1)},
    };
  }

  @Test
  @Parameters(method = "compareToParams")
  public void compareTo(Quality higher, Quality lower) {
    assertThat(lower, lessThan(higher));
    assertThat(higher, greaterThan(lower));
  }

  @Test
  public void compareEquals() {
    new EqualsTester()
      .addEqualityGroup(
          new Quality(0),
          new Quality(0L),
          new Quality(0.0),
          new Quality((byte) 0),
          Quality.ZERO)
      .addEqualityGroup(new Quality(1), new Quality(1.0))
      .addEqualityGroup(new Quality(-1.0), new Quality(-1.0))
      .testEquals();
  }
  
  @Test
  public void toStringContainsValue() throws Exception {
    Double value = Double.valueOf(1.0);
    Quality quality = new Quality(value);
    
    assertThat(quality, hasToString(containsString(value.toString())));
  }
}
