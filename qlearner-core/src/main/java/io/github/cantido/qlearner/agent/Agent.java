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

package io.github.cantido.qlearner.agent;

import com.google.common.util.concurrent.Futures;

import io.github.cantido.qlearner.algorithm.model.ExplorationStrategy;
import io.github.cantido.qlearner.algorithm.model.Quality;
import io.github.cantido.qlearner.algorithm.model.QualityMap;
import io.github.cantido.qlearner.algorithm.quality.QualityUpdater;
import io.github.cantido.qlearner.algorithm.quality.StateActionQuality;
import io.github.cantido.qlearner.client.Action;
import io.github.cantido.qlearner.client.Environment;
import io.github.cantido.qlearner.client.State;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Performs {@link Action}s that will lead to changes in the {@link Environment} 's current
 * {@link State}.
 * 
 * <p>
 * This is the object that will navigate and learn the problem space (the {@code Environment}). It
 * implements the Q-learning algorithm to pick the optimal {@code Action} to take while in each
 * {@code State} in order to maximize the accumulated {@link io.github.cantido.qlearner.algorithm.
 * model.Reward Reward} value.
 * </p>
 */
@NotThreadSafe
public class Agent {
  @Nonnull
  private final Environment environment;

  @Nonnull
  private final ExplorationStrategy explorationStrategy;
  @Nonnull
  private final QualityMap qualityMap;
  @Nonnull
  private final ExecutorService actionExecutorService;
  @Nonnull
  private final QualityUpdater updater;

  @Nullable
  private Step lastStep;
  
  @Nonnull
  private Future<?> lastActionFuture = Futures.immediateFuture(null);

  /**
   * Clients should not be instantiating this object themselves. Please use the {@link AgentBuilder}
   * .
   * 
   * @param environment the source of {@link State} objects
   * @param explorationStrategy the type of exploration to use
   * @param qualityMap where this agent should get and save {@link Quality} values
   * @param actionExecutor the object that should execute chosen {@link Action}s
   * @param updater the object that this agent will use to update {@code Quality} values
   */
  /* package-private */ Agent(
                            Environment environment,
                            ExplorationStrategy explorationStrategy,
                            QualityMap qualityMap,
                            ExecutorService actionExecutor,
                            QualityUpdater updater) {
    this.environment = environment;
    this.explorationStrategy = explorationStrategy;
    this.qualityMap = qualityMap;
    this.actionExecutorService = actionExecutor;
    this.updater = updater;
  }

  /**
   * Remove recent history from this object, so that it does not associate the next {@link State}
   * with the previous {@link Step}.
   * 
   * <p>
   * Use this when you plan to "move" this agent. For instance, if you move this agent from a goal
   * state back to a start state, call this method before your {@link Environment} returns your next
   * {@link State}, so that this agent does not think it can take one step to transition to that
   * starting state.
   * </p>
   */
  public void reset() {
    this.lastStep = null;
    this.lastActionFuture = Futures.immediateFuture(null);
  }

  /**
   * Determine the next {@link Action} to take, and then call its {@link Action#run()} method.
   * 
   * <p>
   * This {@code Agent} will also update the {@link Quality} value of the previous {@link State} and
   * the {@code Action} that got us from there to here, based on the current {@code State}'s reward
   * value.
   * </p>
   */
  public void takeNextAction() {
    try {
      lastActionFuture.get();
    } catch (InterruptedException | ExecutionException futureException) {
      // Let it fail.
    }
    State currentState = environment.getState();
    SortedSet<StateActionQuality> potentialQualities = buildTriplets(currentState);
    Action nextAction = explorationStrategy.getNextAction(potentialQualities);

    lastActionFuture = actionExecutorService.submit(nextAction);

    if (lastStep != null) {
      updater.updateQuality(lastStep, currentState);
    }

    lastStep = new Step(currentState, nextAction);
  }

  private SortedSet<StateActionQuality> buildTriplets(State state) {
    assert state != null : "state must not be null";
    SortedSet<StateActionQuality> pairs = new TreeSet<>();

    // Using streams is not as fast as this plain ol' iteration
    for (Action action : state.getActions()) {
      if (action == null) {
        continue;
      }
      pairs.add(qualityMap.getTriplet(state, action));
    }
    return pairs;
  }

  /**
   * Get the most recent {@code Step} made by this Agent.
   * 
   * <p>
   * If this agent has not yet executed any {@link Action}, there will be no {@code Step} to return.
   * </p>
   * 
   * @return the most recent {@code Step} taken by this object.
   */
  public Optional<Step> getLastStep() {
    return Optional.of(lastStep);
  }
}
