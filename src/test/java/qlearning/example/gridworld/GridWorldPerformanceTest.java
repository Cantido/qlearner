package qlearning.example.gridworld;

import java.time.Duration;
import java.time.Instant;

import qlearning.ExplorationStrategy;
import qlearning.agent.Agent;
import qlearning.agent.AgentBuilder;
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
        GridWorldEnvironment environment = new GridWorldEnvironment();
        QualityMap qualityMap = new QualityHashMap(400);
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
