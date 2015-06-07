package qlearning;

import org.apache.commons.lang3.Validate;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;

/**
 * Performs {@link Action}s that will lead to changes in the {@link Environment}'s current {@link State}.
 * <p>
 * This is the object that will navigate and learn the problem space (the {@code Environment}). It implements the
 * Q-learning algorithm to pick the optimal {@code Action} to take while in each {@code State}.
 */
public class Agent {
    private final Environment environment;
    private final ExplorationStrategy explorationStrategy;
    private final LearningRate learningRate;
    private final DiscountFactor discountFactor;
    private final QualityMap qualityMap;
    
    private Episode currentEpisode;
    
    public Agent(Environment environment,
            ExplorationStrategy explorationStrategy,
            LearningRate learningRate,
            DiscountFactor discountFactor,
            QualityMap qualityMap) {

        Validate.notNull(environment, "Environment cannot be null");
        Validate.notNull(explorationStrategy, "LearningRate cannot be null");
        Validate.notNull(learningRate, "DiscountFactor cannot be null");
        Validate.notNull(discountFactor, "ExplorationStrategy cannot be null");
        Validate.notNull(qualityMap, "QualityMap cannot be null");
        
        this.environment = environment;
        this.explorationStrategy = explorationStrategy;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        this.qualityMap = qualityMap;
        
        this.currentEpisode = new FirstEpisode(explorationStrategy, learningRate, discountFactor, qualityMap);
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
        this.currentEpisode = new FirstEpisode(explorationStrategy, learningRate, discountFactor, qualityMap);
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
