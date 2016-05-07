/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QLearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.agent;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Executor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import qlearning.client.Action;
import qlearning.client.Environment;
import qlearning.client.State;
import qlearning.domain.exploration.ExplorationStrategy;
import qlearning.domain.quality.QualityMap;
import qlearning.domain.quality.QualityUpdater;
import qlearning.domain.quality.StateActionQuality;

/**
 * Performs {@link Action}s that will lead to changes in the {@link Environment}'s current {@link State}.
 * <p>
 * This is the object that will navigate and learn the problem space (the {@code Environment}). It implements the
 * Q-learning algorithm to pick the optimal {@code Action} to take while in each {@code State}.
 */
public class Agent {
    @Nonnull private final Environment environment;

    @Nonnull private final ExplorationStrategy explorationStrategy;
    @Nonnull private final QualityMap qualityMap;
    @Nonnull private final Executor actionExecutor;
    @Nonnull private final QualityUpdater updater;

    @Nullable private Step lastStep;

    public Agent(
    		Environment environment,
    	    ExplorationStrategy explorationStrategy,
    	    QualityMap qualityMap,
    	    Executor actionExecutor,
    	    QualityUpdater updater
    		) {
        this.environment = environment;
        this.explorationStrategy = explorationStrategy;
        this.qualityMap = qualityMap;
        this.actionExecutor = actionExecutor;
        this.updater = updater;
    }

    public void reset() {
        this.lastStep = null;
    }

    /**
     * Determine the next {@link Action} to take, and then call its {@link Action#run()} method.
     * <p>
     * This {@code Agent} will also update the Quality value of the previous {@link State} and the {@code Action} that
     * got us from there to here, based on the current {@code State}'s reward value.
     * </p>
     */
    public void takeNextAction() {
        State currentState = environment.getState();
        SortedSet<StateActionQuality> potentialQualities = buildTriplets(currentState);
        Action nextAction = explorationStrategy.getNextAction(potentialQualities);

        if(lastStep != null) {
            updater.updateQuality(lastStep, currentState);
        }

        lastStep = new Step(currentState, nextAction);

        actionExecutor.execute(nextAction);
    }

    private SortedSet<StateActionQuality> buildTriplets(State state) {
        assert(state != null) : "state must not be null";
        SortedSet<StateActionQuality> pairs = new TreeSet<>();

        // Using streams is not as fast as this plain ol' iteration
        for(Action action : state.getActions()) {
        	if(action == null) continue;
            pairs.add(qualityMap.getTriplet(state, action));
        }
        return pairs;
    }

    public Optional<Step> getLastStep() {
    	Optional<Step> optional = Optional.of(lastStep);
    	if(optional == null) throw new AssertionError("Optional.of returned null.");
        return optional;
    }
}