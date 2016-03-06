package qlearning.example.gridworld;

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


import java.time.Duration;
import java.time.Instant;

import qlearning.ExplorationStrategy;
import qlearning.agent.Agent;
import qlearning.agent.Agent.AgentBuilder;
import qlearning.domain.DiscountFactor;
import qlearning.domain.ExplorationFactor;
import qlearning.domain.LearningRate;
import qlearning.impl.RandomExplorationStrategy;
import qlearning.quality.map.QualityHashMap;
import qlearning.quality.map.QualityMap;
import qlearning.quality.strategy.BackwardInduction;
import qlearning.quality.strategy.QualityUpdateStrategy;

public class GridWorldPerformanceTest {
    private static final int MAX_SUCCESS_COUNT = 50000;

    public static void main(String[] args) {
        GridWorldEnvironment environment = new GridWorldEnvironment(10, 10, 0, 0, 10, 10);
        QualityMap qualityMap = new QualityHashMap(100, 4);
        QualityUpdateStrategy qualityUpdateStrategy = new BackwardInduction();
        
        ExplorationFactor EXPLORATION_FACTOR = new ExplorationFactor(0.1);
        ExplorationStrategy EXPLORATION_STRATEGY = new RandomExplorationStrategy(EXPLORATION_FACTOR);
        DiscountFactor DISCOUNT_FACTOR = new DiscountFactor(1);
        LearningRate LEARNING_RATE = new LearningRate(1);
        
        
        Agent agent = (new AgentBuilder(environment))
                .setExplorationStrategy(EXPLORATION_STRATEGY)
                .setLearningRate(LEARNING_RATE)
                .setDiscountFactor(DISCOUNT_FACTOR)
                .setQualityMap(qualityMap)
                .setQualityUpdateStrategy(qualityUpdateStrategy)
                .getAgent();
        
        Instant start = Instant.now();
        System.out.println("Running " + MAX_SUCCESS_COUNT + " successes, starting " + start.toString());
        
        for (int successCount = 0; successCount < MAX_SUCCESS_COUNT; successCount++) {

            while (!environment.isAtGoalState()) {
                agent.takeNextAction();
            }

            environment.reset();
            agent.resetState();
        }
        
        Instant end = Instant.now();
        
        Duration runTime = Duration.between(start, end).abs();
        
        System.out.println("Finished at " + end + ". Total time taken: " + runTime);
        
    }
}
