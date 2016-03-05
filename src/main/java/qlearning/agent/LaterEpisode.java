package qlearning.agent;

/*
 * #%L
 * QLearner
 * %%
 * Copyright (C) 2012 - 2016 Robert Richter
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import org.apache.commons.lang3.Validate;

import qlearning.Action;
import qlearning.ExplorationStrategy;
import qlearning.State;
import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Reward;
import qlearning.quality.Quality;
import qlearning.quality.map.QualityMap;
import qlearning.quality.strategy.QualityUpdateStrategy;

/**
 * An iteration of the q-learning algorithm that has a previous {@link State} and {@link Action}
 * from which to update a {@link Quality} value.
 */
/* package-private */ class LaterEpisode extends Episode {
    protected final Action previousAction;
    protected final State previousState;
    
    public LaterEpisode(
            State previousState,
            Action previousAction,
            
            ExplorationStrategy explorationStrategy,
            QualityUpdateStrategy qualityUpdateStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {
        
        super(explorationStrategy, qualityUpdateStrategy, learningRate, discountFactor, qualityMap);
        
        this.previousState = Validate.notNull(previousState, "Previous state cannot be null");
        this.previousAction = Validate.notNull(previousAction, "Previous action cannot be null");
    }
    
    
    /**
     * Update the quality of the previous state-action pair, given the desirability of the new state
     */
    @Override
    protected void updateQuality() {
        Quality oldQuality = qualityMap.get(this.previousState, this.previousAction);
        Reward reward = currentState.getReward();
        Quality optimalFutureValueEstimate = qualityMap.getBestQuality(currentState);

        logger.debug(
                "Creating new quality using the following values: (Qt: {}), (a: {}), (Rt+1: {}), (d: {}), (maxQt: {})",
                oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);
        
        Quality newQuality = qualityUpdateStrategy.next(oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);
        

        logger.debug("Updating quality for [{}, {}] to {}", previousState, previousAction, newQuality);

        qualityMap.put(previousState, previousAction, newQuality);
    }

    @Override
    protected Episode getNextEpisode() {
        return new LaterEpisode(
                currentState,
                chosenNextAction,
                explorationStrategy,
                qualityUpdateStrategy,
                learningRate,
                discountFactor,
                qualityMap);
    }
}