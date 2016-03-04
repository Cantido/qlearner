/**
 * This file is part of qlearner
 *
 *  Qlearner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Qlearner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Qlearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.agent;

import org.apache.commons.lang3.Validate;

import qlearning.Action;
import qlearning.Environment;
import qlearning.ExplorationStrategy;
import qlearning.State;
import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.quality.map.QualityMap;
import qlearning.quality.strategy.QualityUpdateStrategy;

/**
 * Performs {@link Action}s that will lead to changes in the {@link Environment}'s current {@link State}.
 * <p>
 * This is the object that will navigate and learn the problem space (the {@code Environment}). It implements the
 * Q-learning algorithm to pick the optimal {@code Action} to take while in each {@code State}.
 */
public class Agent {
    private final Environment environment;
    private final ExplorationStrategy explorationStrategy;
    private final QualityUpdateStrategy qualityUpdateStrategy;
    private final LearningRate learningRate;
    private final DiscountFactor discountFactor;
    private final QualityMap qualityMap;
    
    private Episode currentEpisode;
    
    public Agent(Environment environment,
            ExplorationStrategy explorationStrategy,
            QualityUpdateStrategy qualityUpdateStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {

        Validate.notNull(environment, "Environment cannot be null");
        Validate.notNull(explorationStrategy, "LearningRate cannot be null");
        Validate.notNull(learningRate, "DiscountFactor cannot be null");
        Validate.notNull(discountFactor, "ExplorationStrategy cannot be null");
        Validate.notNull(qualityUpdateStrategy, "QualityUpdateStrategy cannot be null");
        Validate.notNull(qualityMap, "QualityMap cannot be null");
        
        this.environment = environment;
        this.explorationStrategy = explorationStrategy;
        this.qualityUpdateStrategy = qualityUpdateStrategy;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        this.qualityMap = qualityMap;
        
        this.currentEpisode = new FirstEpisode(explorationStrategy, qualityUpdateStrategy, learningRate, discountFactor, qualityMap);
    }

    /**
     * Reset memory of the previous and current states, so we can "pick up the piece and put it elsewhere."
     * <p>
     * Call this method when there will be a change in the current {@code State} that you don't want this {@code Agent}
     * learning from. In some systems (like our {@link qlearning.example.gridworld} example), you must call this method
     * when you have reached the goal state and wish to move back to the start state without messing up your hard-earned
     * Quality values.
     * </p>
     */
    public void resetState() {
        this.currentEpisode = new FirstEpisode(explorationStrategy, qualityUpdateStrategy, learningRate, discountFactor, qualityMap);
    }

    /**
     * Determine the next {@link Action} to take, and then call its {@link Action#execute()} method.
     * <p>
     * This {@code Agent} will also update the Quality value of the previous {@link State} and the {@code Action} that
     * got us from there to here, based on the current {@code State}'s reward value.
     * </p>
     */
    public void takeNextAction() {
        State currentState = environment.getState();        
        currentEpisode = currentEpisode.proceed(currentState);
    }
}