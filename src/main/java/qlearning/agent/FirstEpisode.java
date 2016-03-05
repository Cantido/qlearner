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
import qlearning.ExplorationStrategy;
import qlearning.State;
import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.quality.Quality;
import qlearning.quality.map.QualityMap;
import qlearning.quality.strategy.QualityUpdateStrategy;

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
     * @param explorationStrategy
     *          The {@link ExplorationStrategy} that this and future {@code Episode} objects will use
     *          to determine when to explore
     * @param learningRate
     *          The {@link LearningRate} that this and future {@code Episode} objects will use
     *          to update {@code Quality} values
     * @param discountFactor The {@link DiscountFactor} that this and future {@code Episode} objects will use
     *          to update {@code Quality} values
     * @param qualityMap The {@link QualityMap} that this and future {@code Episode} objects will use
     *          to get and update {@code Quality} values
     */
    public FirstEpisode(
            ExplorationStrategy explorationStrategy,
            QualityUpdateStrategy qualityUpdateStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {
        super(explorationStrategy, qualityUpdateStrategy, learningRate, discountFactor, qualityMap);
    }
    
    @Override
    protected Episode getNextEpisode() {
        return new LaterEpisode(currentState, chosenNextAction, explorationStrategy, qualityUpdateStrategy, learningRate, discountFactor, qualityMap);
    }

    @Override
    protected void updateQuality() {
        // Intentionally left blank. 
    }
}
