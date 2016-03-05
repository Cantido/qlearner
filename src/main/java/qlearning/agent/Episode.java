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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.Action;
import qlearning.ExplorationStrategy;
import qlearning.State;
import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.StateActionQuality;
import qlearning.quality.Quality;
import qlearning.quality.map.QualityMap;
import qlearning.quality.strategy.QualityUpdateStrategy;

/**
 * One full iteration of the q-learning algorithm, starting at
 * a certain {@link State}, taking an {@link Action}, and updating
 * the {@link Quality} for that pair.
 * 
 * <p>The q-learning algorithm is implemented by calling {@link Episode#proceed(State)}
 * on a series of {@code Episode} objects, passing the new {@link State} to this {@code Episode}
 * to learn the effect of the last {@link Action} taken.  
 */
/* package-private */ abstract class Episode {
    @SuppressWarnings("null")
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected final ExplorationStrategy explorationStrategy;
    protected final QualityUpdateStrategy qualityUpdateStrategy;
    protected final LearningRate learningRate;
    protected final DiscountFactor discountFactor;
    protected final QualityMap qualityMap;
    
    protected State currentState = State.NULL;
    protected Set<Runnable> possibleNextActions = new TreeSet<>();
    protected Runnable chosenNextAction = new Runnable() {
        @Override
        public void run() {
            // Do nothing
        }
    };
    
    /**
     * Create an {@code Episode} that represents one iteration of the q-learning algorithm.
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
    protected Episode(
            ExplorationStrategy explorationStrategy,
            QualityUpdateStrategy qualityUpdateStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {
        
        this.explorationStrategy = Validate.notNull(explorationStrategy, "LearningRate cannot be null");
        this.qualityUpdateStrategy = Validate.notNull(qualityUpdateStrategy, "QualityUpdateStrategy cannot be null");
        this.learningRate = Validate.notNull(learningRate, "DiscountFactor cannot be null");
        this.discountFactor = Validate.notNull(discountFactor, "ExplorationStrategy cannot be null");
        this.qualityMap = Validate.notNull(qualityMap, "QualityMap cannot be null");
    }
    
    /**
     * Executes one full iteration of the q-learning algorithm.
     * 
     * @param currentState the state to proceed from in this iteration
     * @return an {@code Episode} that represents the next iteration
     */
    public Episode proceed(final State currentState) {
        this.currentState = validateCurrentState(currentState);
        
        possibleNextActions = validatePossibleNextActions(this.currentState.getActions());
        
        Collection<StateActionQuality> potentialQualities = buildTriplets(currentState, possibleNextActions);
        
        chosenNextAction = validateNextAction(explorationStrategy.getNextAction(potentialQualities));
        
        updateQuality();
        
        chosenNextAction.run();

        return getNextEpisode();
    }
    
    /**
     * Update the {@link Quality} value based on this episode's performance.
     */
    protected abstract void updateQuality();
    
    /**
     * Get the {@code Episode} which represents the next algorithm iteration 
     * @return the next iteration's {@code Episode}
     */
    protected abstract Episode getNextEpisode(); 
    
    private Collection<StateActionQuality> buildTriplets(State state, Set<Runnable> possibleActions) {
    	assert(state != null) : "state must not be null";
    	assert(possibleActions != null) : "possibleActions must not be null";
    	
        Collection<StateActionQuality> pairs = new ArrayList<>(possibleActions.size());
        
        for(Runnable action : possibleActions) {
            Quality quality = qualityMap.get(state, action);
            
            pairs.add(new StateActionQuality(state, action, quality));
            
            logger.debug("Potential action: {}, quality: {}", action, quality);
        }
        return pairs;
    }
    
    private static State validateCurrentState(State state) {
        return Validate.notNull(state, "Current state cannot be null");
    }
    
    private static Set<Runnable> validatePossibleNextActions(Set<Runnable> nextActions) {
        Validate.notNull(nextActions,
                "The list of possible actions from a state cannot be null. " +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        Validate.isTrue(!nextActions.isEmpty(),
                "The list of possible actions from a state cannot be empty. " +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        return nextActions;
    }
    
    /**
     * Validate the given {@link Action} as an action that will be taken in the next iteration
     * @param nextAction the {@code Action} to validate
     * @return the {@code Action} that was validated
     * 
     * @throws NullPointerException of the given {@code Action} is null
     */
    private static Runnable validateNextAction(Runnable nextAction) {
        Validate.notNull(nextAction, 
                "The action returned by the ExplorationStrategy cannot be null." +
                "If it is possible for the agent to take no action, consider creating a \"Wait\" action.");
        return nextAction;
    }
}
