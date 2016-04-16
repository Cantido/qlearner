package qlearning.agent;

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

import qlearning.client.Action;
import qlearning.client.Environment;
import qlearning.domain.exploration.ExplorationStrategy;
import qlearning.client.State;
import qlearning.agent.executors.DirectExecutor;
import qlearning.domain.learning.DiscountFactor;
import qlearning.domain.exploration.ExplorationFactor;
import qlearning.domain.learning.LearningRate;
import qlearning.domain.exploration.RandomExplorationStrategy;
import qlearning.domain.quality.QualityHashMap;
import qlearning.domain.quality.QualityMap;
import qlearning.domain.quality.BackwardInduction;
import qlearning.domain.quality.QualityUpdateStrategy;

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
    
    private final AgentBuilder builder;
    
    private Episode currentEpisode;
    
    private Agent(AgentBuilder agentBuilder) {
        this.builder = agentBuilder;
        
        this.environment = agentBuilder.environment;
        this.currentEpisode = new FirstEpisode(builder);
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
        this.currentEpisode = new FirstEpisode(builder);
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
