package qlearning.impl;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.Action;
import qlearning.ExplorationStrategy;
import qlearning.State;
import qlearning.domain.ExplorationFactor;
import qlearning.domain.Quality;

/**
 * A simple {@link ExplorationStrategy} which will choose a random {@link Action} some of the time.
 */
public class RandomExplorationStrategy implements ExplorationStrategy {
    Logger logger =  LoggerFactory.getLogger("qlearning.impl.RandomExplorationStrategy");
    
    private ExplorationFactor explorationFactor;
    private static ExplorationFactor DEFAULT_EXPLORATION_FACTOR = new ExplorationFactor(0.1);
    private Random random;
    
    /**
     * Create a new {@link RandomExplorationStrategy} with the a default exploration factor of 0.1.
     * 
     * @see #setExplorationFactor(double)
     */
    public RandomExplorationStrategy() {
        this(DEFAULT_EXPLORATION_FACTOR, ThreadLocalRandom.current());
    }
    
    /**
     * Create a new {@link RandomExplorationStrategy} with the given exploration factor.
     * 
     * @see #setExplorationFactor(double)
     * 
     * @param explorationFactor the exploration factor to set, in the range [0, 1]
     */
    public RandomExplorationStrategy(ExplorationFactor explorationFactor) {
        this(explorationFactor, ThreadLocalRandom.current());
    }
    
    public RandomExplorationStrategy(ExplorationFactor explorationFactor, Random random) {
        this.explorationFactor = Validate.notNull(explorationFactor);
        this.random = Validate.notNull(random);
    }
    
    /**
     * Set the exploration factor, which determines how often the agent will choose random actions over desirable ones
     * in order to explore the problem space.
     * <p>
     * A factor of 0 will make the agent never choose random actions. A factor of 1 will make the agent always choose
     * random actions. The agent will still update quality values correctly based on the resulting state's reward, so
     * setting the exploration factor to 0 would allow you to perform a random search through the problem space.
     * </p>
     * 
     * @param ef the new exploration factor to set, in the range [0, 1]
     */
    public void setExplorationFactor(ExplorationFactor ef) {
        this.explorationFactor = Validate.notNull(ef);
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
    public Action getNextAction(Map<Pair<State, Action>, Quality> stateActionQualities) {
        
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
    
    
    private Action getBestAction(Map<Pair<State, Action>, Quality> stateActionQualities) {
        logger.debug("Determining best action from possible actions: {}", stateActionQualities);

        SortedSet<ImmutablePair<Quality, Action>> actionQualities = new TreeSet<>();

        for (Map.Entry<Pair<State, Action>, Quality> stateActionQuality : stateActionQualities.entrySet()) {
            Quality quality = stateActionQuality.getValue();
            Action action = stateActionQuality.getKey().getRight();
            
            actionQualities.add(ImmutablePair.of(quality, action));
        }

        logger.debug("Possible actions and qualities: {}", actionQualities);

        return actionQualities.last().getRight();
    }

    private Action getRandomAction(Map<Pair<State, Action>, Quality> stateActionQualities) {

        List<Pair<State, Action>> stateActions = new ArrayList<>(stateActionQualities.keySet());
        
        return stateActions.get(random.nextInt(stateActions.size())).getRight();
    }
}
