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

package qlearning.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.Action;
import qlearning.ExplorationStrategy;
import qlearning.domain.ExplorationFactor;
import qlearning.domain.StateActionQuality;

/**
 * A simple {@link ExplorationStrategy} which will choose a random {@link Action} some of the time.
 */
public class RandomExplorationStrategy implements ExplorationStrategy {
    private Logger logger =  LoggerFactory.getLogger(getClass());
    
    private final ExplorationFactor explorationFactor;
    private final Random random;
    
    /**
     * Create a new {@link RandomExplorationStrategy} with the given exploration factor.
     * 
     * @see #setExplorationFactor(double)
     * 
     * @param explorationFactor the exploration factor to set
     */
    public RandomExplorationStrategy(ExplorationFactor explorationFactor) {
        this(explorationFactor, ThreadLocalRandom.current());
    }
    
    public RandomExplorationStrategy(ExplorationFactor explorationFactor, Random random) {
        this.explorationFactor = Validate.notNull(explorationFactor);
        this.random = Validate.notNull(random);
    }
    
    /**
     * Get the exploration factor, which determines how often the agent will choose random actions over desirable ones
     * in order to explore the problem space.
     * 
     * @return the exploration factor
     */
    public ExplorationFactor getExplorationFactor() {
        return this.explorationFactor;
    }
    
    /**
     * Choose either the best possible action, or a random action.
     *
     * @see RandomExplorationStrategy#setExplorationFactor(ExplorationFactor)
     */
    @Override
    public Action getNextAction(Collection<StateActionQuality> stateActionQualities) {
        
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
    
    
    private Action getBestAction(Collection<StateActionQuality> stateActionQualities) {
    	assert(stateActionQualities != null) : "stateActionQualities must not be null";
    	
        logger.debug("Determining best action from possible actions: {}", stateActionQualities);
        return Collections.max(stateActionQualities).getAction();
    }

    private Action getRandomAction(Collection<StateActionQuality> stateActionQualities) {
    	assert(stateActionQualities != null) : "stateActionQualities must not be null";
    	
        Integer randomIndex = random.nextInt(stateActionQualities.size());
        StateActionQuality randomTriplet = (StateActionQuality) stateActionQualities.toArray()[randomIndex];
        
        return randomTriplet.getAction();
    }
}
