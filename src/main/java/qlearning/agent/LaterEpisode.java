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

import qlearning.Action;
import qlearning.State;
import qlearning.agent.Agent.AgentBuilder;
import qlearning.domain.Reward;
import qlearning.quality.Quality;

/**
 * An iteration of the q-learning algorithm that has a previous {@link State} and {@link Action}
 * from which to update a {@link Quality} value.
 */
/* package-private */ class LaterEpisode extends Episode {
    protected final Action previousAction;
    protected final State previousState;
    
    public LaterEpisode(State previousState, Action chosenNextAction, AgentBuilder builder) {
        super(builder);
        this.previousState = previousState;
        this.previousAction = chosenNextAction;
    }

    /**
     * Update the quality of the previous state-action pair, given the desirability of the new state
     */
    @Override
    protected void updateQuality() {
        State currentState = this.currentState;
        if(currentState == null) {
            throw new NullPointerException("Current state was null");
        }
        
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
        Action nextAction = chosenNextAction;
        if(nextAction == null) {
            throw new NullPointerException("Chosen next action was null");
        }
        State currentState = this.currentState;
        if(currentState == null) {
            throw new NullPointerException("Chosen next action was null");
        }
        return new LaterEpisode(
                currentState,
                nextAction,
                builder);
    }
}