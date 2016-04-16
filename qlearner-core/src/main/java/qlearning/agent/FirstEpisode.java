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

import qlearning.client.Action;
import qlearning.client.State;
import qlearning.agent.Agent.AgentBuilder;
import qlearning.domain.quality.Quality;

/**
 * An iteration of the q-learning algorithm that does not have a {@link State} or {@link Action}
 * from which to update a {@link Quality} value. As the name implies, this is the starting point,
 * and {@link #proceed(State)} returns a {@link LaterEpisode} object, since we then have a history to evaluate.
 * 
 * <p>Calling {@link #proceed(State)} on this object will not update any {@code Quality}
 * values, because there is no previous {@code State} or {@code Action} to update
 * a {@code Quality} for. It will, however, return a new {@code Episode} with that data.
 */
/* package-private */ class FirstEpisode extends Episode {
    
    /**
     * Create an {@code Episode} that does not have a previous {@link State} or {@link Action}
     * from which to build a {@link Quality} value.
     * 
     * @param builder the builder containing the Agent's configuration values
     */
    public FirstEpisode(AgentBuilder builder) {
        super(builder);
    }
    
    @Override
    protected Episode getNextEpisode() {
        Action nextAction = chosenNextAction;
        State currentState = this.currentState;
        if(nextAction == null) {
            throw new NullPointerException("Chosen next action was null");
        }
        if(currentState == null) {
            throw new NullPointerException("Chosen next action was null");
        }
        return new LaterEpisode(currentState, nextAction, builder);
    }

    @Override
    protected void updateQuality() {
        // Intentionally left blank. 
    }
}
