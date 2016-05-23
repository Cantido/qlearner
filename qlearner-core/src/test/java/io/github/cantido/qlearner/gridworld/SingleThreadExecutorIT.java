package io.github.cantido.qlearner.gridworld;

import io.github.cantido.qlearner.agent.Agent;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An integration test that uses a single-worker-thread {@link ExecutorService} to validate
 * some of the package's thread-safety.
 */
public class SingleThreadExecutorIT {
  private static final int MAX_SUCCESS_COUNT = 10000;
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  //private final ExecutorService executorService = MoreExecutors.newDirectExecutorService();
  private GridWorldBuilder gridWorldBuilder = new GridWorldBuilder();
  private Agent agent;
  private GridWorldEnvironment environment;
  
  /**
   * Build an agent that uses a single-thread executor.
   */
  @Before
  public void setUp() {
    gridWorldBuilder.setExecutorService(executorService);
    agent = gridWorldBuilder.getAgent();
    environment = (GridWorldEnvironment) gridWorldBuilder.getEnvironment();
  }
  
  /**
   * Iterates many times using the single thread executor in order to shake out some
   * potential threading bugs.
   * @throws Exception if any exception happens
   */
  @Test
  public void iteratesWithSingleThreadExecutor() throws Exception {
    for (int successCount = 0; successCount < MAX_SUCCESS_COUNT; successCount++) {

      while (!environment.isAtGoalState()) {
        agent.takeNextAction();
        agent.await();
      }

      environment.reset();
      agent.reset();
    }
    executorService.shutdown();
  }
}
