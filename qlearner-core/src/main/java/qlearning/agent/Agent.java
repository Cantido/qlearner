package qlearning.agent;

import java.util.*;
import java.util.concurrent.Executor;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qlearning.client.Action;
import qlearning.client.Environment;
import qlearning.domain.exploration.ExplorationStrategy;
import qlearning.client.State;
import qlearning.agent.executors.DirectExecutor;
import qlearning.domain.learning.DiscountFactor;
import qlearning.domain.exploration.ExplorationFactor;
import qlearning.domain.learning.LearningRate;
import qlearning.domain.exploration.RandomExplorationStrategy;
import qlearning.domain.learning.Reward;
import qlearning.domain.quality.*;

/**
 * Performs {@link Action}s that will lead to changes in the {@link Environment}'s current {@link State}.
 * <p>
 * This is the object that will navigate and learn the problem space (the {@code Environment}). It implements the
 * Q-learning algorithm to pick the optimal {@code Action} to take while in each {@code State}.
 */
public class Agent {
    /**
     * Builds {@link Agent} objects using the Builder pattern:
     * 
     * <pre>
     * Agent agent = 
     *  new AgentBuilder()
     *        .setEnvironment(environment)
     *        .setExplorationStrategy(EXPLORATION_STRATEGY)
     *        .setLearningRate(LEARNING_RATE)
     *        .setDiscountFactor(DISCOUNT_FACTOR)
     *        .setQualityMap(qualityMap)
     *        .getAgent();
     * </pre>
     */
    public static class AgentBuilder {
        private static final ExplorationFactor DEFAULT_EXPLORATION_FACTOR = new ExplorationFactor(0.2);
        
        private Environment environment;
        private Executor actionExecutor = new DirectExecutor();
        private DiscountFactor discountFactor = new DiscountFactor(1);
        private ExplorationStrategy explorationStrategy = new RandomExplorationStrategy(DEFAULT_EXPLORATION_FACTOR);
        private LearningRate learningRate = new LearningRate(1);
        private QualityMap qualityMap = new QualityHashMap();
        private QualityUpdateStrategy qualityUpdateStrategy = new BackwardInduction();

        public AgentBuilder(Environment environment) {
            this.environment = environment;
        }

        public Executor getActionExecutor() {
            return actionExecutor;
        }
        public Agent getAgent() {
            return new Agent(this);
        }
        public DiscountFactor getDiscountFactor() {
            return discountFactor;
        }
        public Environment getEnvironment() {
            return environment;
        }
        public ExplorationStrategy getExplorationStrategy() {
            return explorationStrategy;
        }
        public LearningRate getLearningRate() {
            return learningRate;
        }
        public QualityMap getQualityMap() {
            return qualityMap;
        }
        
        public QualityUpdateStrategy getQualityUpdateStrategy() {
            return qualityUpdateStrategy;
        }
        
        public void setActionExecutor(Executor actionExecutor) {
            this.actionExecutor = actionExecutor;
        }

        public AgentBuilder setDiscountFactor(DiscountFactor discountFactor) {
            this.discountFactor = discountFactor;
            return this;
        }

        public AgentBuilder setEnvironment(Environment environment) {
            this.environment = environment;
            return this;
        }

        public AgentBuilder setExecutor(Executor executor) {
            this.actionExecutor = executor;
            return this;
        }

        public AgentBuilder setExplorationStrategy(ExplorationStrategy explorationStrategy) {
            this.explorationStrategy = explorationStrategy;
            return this;
        }

        public AgentBuilder setLearningRate(LearningRate learningRate) {
            this.learningRate = learningRate;
            return this;
        }
        
        public AgentBuilder setQualityMap(QualityMap qualityMap) {
            this.qualityMap = qualityMap;
            return this;
        }

        public AgentBuilder setQualityUpdateStrategy(QualityUpdateStrategy qualityUpdateStrategy) {
            this.qualityUpdateStrategy = qualityUpdateStrategy;
            return this;
        }
    }
    
    private final Environment environment;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ExplorationStrategy explorationStrategy;
    private final QualityMap qualityMap;
    private final Executor actionExecutor;
    private final QualityUpdater updater;

    private Step lastStep;

    private Agent(Agent.AgentBuilder agentBuilder) {
        this.environment = agentBuilder.getEnvironment();
        this.explorationStrategy = Validate.notNull(agentBuilder.getExplorationStrategy(), "LearningRate cannot be null");
        this.qualityMap = Validate.notNull(agentBuilder.getQualityMap(), "QualityMap cannot be null");
        this.actionExecutor = Validate.notNull(agentBuilder.getActionExecutor(), "Executor cannot be null");

        updater = new QualityUpdater(
                qualityMap,
                agentBuilder.getQualityUpdateStrategy(),
                agentBuilder.getLearningRate(),
                agentBuilder.getDiscountFactor());
    }

    public void reset() {
        this.lastStep = null;
    }

    /**
     * Determine the next {@link Action} to take, and then call its {@link Action#run()} method.
     * <p>
     * This {@code Agent} will also update the Quality value of the previous {@link State} and the {@code Action} that
     * got us from there to here, based on the current {@code State}'s reward value.
     * </p>
     */
    public void takeNextAction() {
        State currentState = environment.getState();
        SortedSet<StateActionQuality> potentialQualities = buildTriplets(currentState);
        Action nextAction = explorationStrategy.getNextAction(potentialQualities);

        if(lastStep != null) {
            updater.updateQuality(lastStep, currentState);
        }

        lastStep = new Step(currentState, nextAction);

        actionExecutor.execute(nextAction);
    }

    private SortedSet<StateActionQuality> buildTriplets(State state) {
        assert(state != null) : "state must not be null";
        SortedSet<StateActionQuality> pairs = new TreeSet<>();

        // Using streams is not as fast as this plain ol' iteration
        for(Action action : state.getActions()) {
            pairs.add(buildTriplet(state, action));
        }
        return pairs;
    }

    private StateActionQuality buildTriplet(State state, Action action) {
        return new StateActionQuality(state, action, qualityMap.get(state, action));
    }

    private static class QualityUpdater {
        private final QualityMap qualityMap;
        private final QualityUpdateStrategy strategy;
        private final LearningRate learningRate;
        private final DiscountFactor discountFactor;

        public QualityUpdater(QualityMap qualityMap, QualityUpdateStrategy strategy, LearningRate learningRate, DiscountFactor discountFactor) {
            this.qualityMap = qualityMap;
            this.strategy = strategy;
            this.learningRate = learningRate;
            this.discountFactor = discountFactor;
        }

        public void updateQuality(Step stepTaken, State resultingState) {
            State previousState = stepTaken.getStartingState();
            Action actionTaken = stepTaken.getLeavingAction();

            Quality oldQuality = qualityMap.get(previousState, actionTaken);

            Reward reward = resultingState.getReward();
            Quality optimalFutureValueEstimate = qualityMap.getBestQuality(resultingState);

            Quality newQuality = this.strategy.next(oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);

            qualityMap.put(previousState, actionTaken, newQuality);
        }
    }
}
