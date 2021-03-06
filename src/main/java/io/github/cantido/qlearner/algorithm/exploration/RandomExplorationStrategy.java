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

import io.github.cantido.qlearner.algorithm.model.ExplorationFactor;
import io.github.cantido.qlearner.algorithm.model.ExplorationStrategy;
import io.github.cantido.qlearner.algorithm.quality.StateActionQuality;
import io.github.cantido.qlearner.client.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.SortedSet;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * A simple {@link ExplorationStrategy} which will choose a random {@link Action} some of the time.
 */
@Immutable
@ThreadSafe
public class RandomExplorationStrategy implements ExplorationStrategy {
  @SuppressWarnings("null")
  @Nonnull
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Nonnull
  private final ExplorationFactor explorationFactor;
  @Nonnull
  private final Random random;

  /**
   * Create a new {@link RandomExplorationStrategy} with the given exploration factor.
   * 
   * @param explorationFactor the exploration factor to set
   */
  @SuppressWarnings("null")
  public RandomExplorationStrategy(ExplorationFactor explorationFactor) {
    this(explorationFactor, ThreadLocalRandom.current());
  }

  /**
   * Create a {@code RandomExplorationStrategy} that uses the given exploration factor and source of
   * randomness to determine whether or not to explore.
   * 
   * @param explorationFactor this object's propensity to explore
   * @param random the source of randomness to make exploration decisions with
   */
  public RandomExplorationStrategy(ExplorationFactor explorationFactor, Random random) {
    this.explorationFactor = explorationFactor;
    this.random = random;
  }

  /**
   * Get the exploration factor, which determines how often the agent will choose random
   * gridworld.actions over desirable ones in order to explore the problem space.
   * 
   * @return this strategy's propensity to explore.
   */
  public ExplorationFactor getExplorationFactor() {
    return this.explorationFactor;
  }

  @Override
  public Action getNextAction(SortedSet<StateActionQuality> stateActionQualities) {

    Action nextAction;

    Double checkValue = random.nextDouble();

    if (explorationFactor.shouldExplore(checkValue)) {
      nextAction = getRandomAction(stateActionQualities);
      logger.debug("Should explore, chose random action: {}", nextAction);
    } else {
      nextAction = getBestAction(stateActionQualities);
      logger.debug("Should not explore, chose best action: {}", nextAction);
    }

    return nextAction;
  }


  private Action getBestAction(SortedSet<StateActionQuality> stateActionQualities) {
    assert stateActionQualities != null : "stateActionQualities must not be null";

    logger.debug("Determining best action from possible gridworld.actions: {}",
        stateActionQualities);
    return stateActionQualities.last().getAction();
  }

  private Action getRandomAction(SortedSet<StateActionQuality> stateActionQualities) {
    assert stateActionQualities != null : "stateActionQualities must not be null";

    Integer randomIndex = random.nextInt(stateActionQualities.size());
    StateActionQuality randomTriplet =
        (StateActionQuality) stateActionQualities.toArray()[randomIndex];

    return randomTriplet.getAction();
  }
}
