package io.github.cantido.qlearner.gridworld;

import io.github.cantido.qlearner.agent.Agent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An integration test that uses a single-worker-thread {@link ExecutorService} to validate
 * some of the package's thread-safety.
 */
public class SingleThreadExecutorIT {
  /**
   * The time limit for these tests.
   * 
   * <p>
   * If there is a problem with the Q-learning algorithm, the values will never converge. That will
   * cause the tests to run too long. If this test fails due to a timeout, we can be pretty sure
   * that the algorithm is not converging as it should.
   * </p>
   */
  @Rule
  public Timeout timeout = Timeout.seconds(1);
  
  private static final int MAX_SUCCESS_COUNT = 1000;
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
