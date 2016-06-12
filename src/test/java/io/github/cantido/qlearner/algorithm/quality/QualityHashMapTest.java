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
import static org.mockito.Mockito.when;

import io.github.cantido.qlearner.algorithm.model.Quality;
import io.github.cantido.qlearner.algorithm.model.QualityMap;
import io.github.cantido.qlearner.algorithm.quality.QualityHashMap;
import io.github.cantido.qlearner.client.Action;
import io.github.cantido.qlearner.client.State;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"null", "javadoc"})
public class QualityHashMapTest {
  @Rule
  public MockitoRule mockito = MockitoJUnit.rule();

  QualityMap map = new QualityHashMap();
  @Mock
  State state;
  @Mock
  Action highestAction;
  @Mock
  Action middleAction;
  @Mock
  Action worstAction;
  Quality lowestQuality = new Quality(-1.0);
  Quality middleQuality = new Quality(0.0);
  Quality highestQuality = new Quality(1.0);
  Quality defaultQuality = map.getDefaultQuality();

  Set<Action> actions = new HashSet<>();

  @Before
  public void setUp() {
    actions.add(worstAction);
    actions.add(middleAction);
    actions.add(highestAction);

    when(state.getActions()).thenReturn(actions);
  }

  public void fillMap() {
    map.put(state, highestAction, lowestQuality);
    map.put(state, middleAction, middleQuality);
    map.put(state, highestAction, highestQuality);
  }

  @Test
  public void storesQuality() {
    fillMap();

    Quality gotQuality = map.get(state, highestAction);

    assertThat(gotQuality, is(highestQuality));
  }

  @Test
  public void returnsDefaultQualityForBestWhenEmpty() {
    Quality actualQuality = map.getBestQuality(state);

    assertThat(actualQuality, is(defaultQuality));
  }

  @Test
  public void returnsDefaultQualityForActionWhenEmpty() {
    Quality actualQuality = map.get(state, highestAction);

    assertThat(actualQuality, is(defaultQuality));
  }

  @Test
  public void getsHighestQualityForBest() {
    fillMap();

    Quality actualQuality = map.getBestQuality(state);

    assertThat(actualQuality, is(highestQuality));
  }

  @Test
  public void willNotDeleteDuplicateQualities() {
    fillMap();

    map.put(state, highestAction, middleQuality);
    map.put(state, middleAction, middleQuality);

    assertThat(map.getBestQuality(state), is(middleQuality));

    map.put(state, highestAction, highestQuality);
    map.put(state, middleAction, middleQuality);

    assertThat(map.getBestQuality(state), is(highestQuality));
  }
}
