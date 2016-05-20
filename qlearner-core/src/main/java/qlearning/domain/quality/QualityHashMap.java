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

package qlearning.domain.quality;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qlearning.client.Action;
import qlearning.client.State;
import qlearning.domain.model.Quality;
import qlearning.domain.model.QualityMap;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * A data structure that stores {@link Quality} values in a hashed form.
 */
@NotThreadSafe
public class QualityHashMap implements QualityMap {
  @SuppressWarnings("null")
  @Nonnull
  private static final Logger logger = LoggerFactory.getLogger(QualityHashMap.class);
  @Nonnegative
  private final int expectedAverageActionsPerState;

  /**
   * Mapping of State-Action pairs to their Quality value.
   */
  @Nonnull
  private final Map<ImmutablePair<State, Action>, Quality> actionQualities;
  /**
   * An optimization; stores the best quality for each state.
   * 
   * <p>Be careful if you are changing this data structure: it is possible to have two equivalent
   * quality values for a given state, and then when we update any of the gridworld.actions with
   * that quality, we would lose that quality for all gridworld.actions.</p>
   */
  @Nonnull
  private final Map<State, PriorityQueue<Quality>> bestQualities;
  @SuppressWarnings("null")
  @Nonnull
  private Quality defaultQuality = Quality.ZERO;

  /**
   * Constructs an empty {@code QualityHashMap} with a default number of expected total states (16)
   * and a default number of expected actions per state (11).
   */
  public QualityHashMap() {
    // Will just match the default PriorityQueue size
    expectedAverageActionsPerState = 11;
    actionQualities = new HashMap<>();
    bestQualities = new HashMap<>();
  }

  /**
   * Constructs an empty {@code QualityHashMap} with a specified number of expected total states and
   * number of expected actions per state.
   * 
   * <p>
   * Specifying these values is only a performance optimization. It does not change the
   * functionality of this object.
   * </p>
   * 
   * @param expectedStates the approximate total count of states this object will store.
   * @param actionsPerState the average count of actions that each stored state will have.
   */
  public QualityHashMap(@Nonnegative int expectedStates, @Nonnegative int actionsPerState) {
    if (expectedStates < 0) {
      throw new IllegalArgumentException(
          "Was given a negative expectedStates number, which is invalid. Got: " + expectedStates);
    }
    if (actionsPerState < 0) {
      throw new IllegalArgumentException(
          "Was given a negative actionsPerState number, which is invalid. Got: " + actionsPerState);
    }

    expectedAverageActionsPerState = actionsPerState;
    actionQualities = new HashMap<>(expectedStates * actionsPerState);
    bestQualities = new HashMap<>(expectedStates);
  }

  /**
   * Set the {@code Quality} value that will be returned for {@code State}-{@code Action} pairs that
   * this object has not yet stored.
   * 
   * @param defaultQuality the default {@code Quality} value.
   */
  public void setDefaultQuality(Quality defaultQuality) {
    this.defaultQuality = defaultQuality;
  }

  @Override
  public Quality getDefaultQuality() {
    return this.defaultQuality;
  }

  @Override
  public void put(State state, Action action, Quality quality) {
    Quality oldQuality = get(state, action);

    actionQualities.put(new ImmutablePair<>(state, action), quality);

    PriorityQueue<Quality> queueToUpdate;

    if (bestQualities.containsKey(state)) {
      queueToUpdate = bestQualities.get(state);
      queueToUpdate.remove(oldQuality);
    } else {
      queueToUpdate =
          new PriorityQueue<>(expectedAverageActionsPerState, Quality.DESCENDING_ORDER);
      bestQualities.put(state, queueToUpdate);
    }

    logger.debug("Updating quality for [{}, {}] to {}", state, action, quality);

    queueToUpdate.add(quality);
  }

  @Override
  public Quality get(State state, Action action) {
    Quality quality = actionQualities.get(new ImmutablePair<>(state, action));

    if (quality == null) {
      return defaultQuality;
    }

    return quality;
  }

  @Override
  public Quality getBestQuality(State state) {
    Quality bestQuality = defaultQuality;

    if (bestQualities.containsKey(state)) {
      bestQuality = bestQualities.get(state).peek();
    }
    if (bestQuality == null) {
      return defaultQuality;
    }

    return bestQuality;
  }

  @Override
  public StateActionQuality getTriplet(State state, Action action) {
    return new StateActionQuality(state, action, this.get(state, action));
  }
}
