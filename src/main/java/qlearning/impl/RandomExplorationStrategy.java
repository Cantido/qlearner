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
    Logger logger =  LoggerFactory.getLogger("qlearning.impl.RandomExplorationStrategy");
    
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
        logger.debug("Determining best action from possible actions: {}", stateActionQualities);
        return Collections.max(stateActionQualities).getAction();
    }

    private Action getRandomAction(Collection<StateActionQuality> stateActionQualities) {
        Integer randomIndex = random.nextInt(stateActionQualities.size());
        StateActionQuality randomTriplet = (StateActionQuality) stateActionQualities.toArray()[randomIndex];
        
        return randomTriplet.getAction();
    }
}
