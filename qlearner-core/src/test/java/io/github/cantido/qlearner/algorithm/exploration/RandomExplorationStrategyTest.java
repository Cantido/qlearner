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

package io.github.cantido.qlearner.algorithm.exploration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertThat;

import io.github.cantido.qlearner.algorithm.exploration.RandomExplorationStrategy;
import io.github.cantido.qlearner.algorithm.model.ExplorationFactor;
import io.github.cantido.qlearner.algorithm.model.Quality;
import io.github.cantido.qlearner.algorithm.quality.StateActionQuality;
import io.github.cantido.qlearner.client.Action;
import io.github.cantido.qlearner.client.State;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Tests for {@link RandomExplorationStrategy}.
 */
@SuppressWarnings({"null"})
public class RandomExplorationStrategyTest {
  /**
   * JUnit {@link Rule} to add {@link Mockito} functionality.
   */
  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  Random random;

  @Mock
  State state;
  @Mock
  Action goodAction;
  @Mock
  Action badAction;

  Quality goodQuality;
  Quality badQuality;

  StateActionQuality goodTriplet;
  StateActionQuality badTriplet;

  SortedSet<StateActionQuality> stateActionQualities;


  /**
   * Build a {@link StateActionQuality} triplet with a higher {@link Quality} and a lower
   * {@code Quality}.
   */
  @Before
  public void setUp() {
    goodQuality = new Quality(1.0);
    badQuality = new Quality(0.0);

    goodTriplet = new StateActionQuality(state, goodAction, goodQuality);
    badTriplet = new StateActionQuality(state, badAction, badQuality);

    stateActionQualities = new TreeSet<>();
    stateActionQualities.add(goodTriplet);
    stateActionQualities.add(badTriplet);
  }

  /**
   * If a {@link RandomExplorationStrategy} has an {@link ExplorationFactor} of zero, then it
   * should never explore, and always pick the best action.
   */
  @Test
  public void nonexploringStrategyGetsBestAction() {
    RandomExplorationStrategy strategy =
        new RandomExplorationStrategy(ExplorationFactor.NEVER_EXPLORE, random);

    Action actualAction = strategy.getNextAction(stateActionQualities);

    assertThat(actualAction, is(goodAction));
  }

  /**
   * If a {@link RandomExplorationStrategy} has an {@link ExplorationFactor} of one, then it
   * should always explore, and it should pick an action randomly.
   */
  @Test
  public void alwaysExploringStrategySelectsRandomAction() {
    RandomExplorationStrategy strategy =
        new RandomExplorationStrategy(ExplorationFactor.ALWAYS_EXPLORE, random);

    Action actualAction = strategy.getNextAction(stateActionQualities);

    assertThat(actualAction, isOneOf(goodAction, badAction));
  }
}
