package io.github.cantido.qlearner.gridworld;

import io.github.cantido.qlearner.agent.Agent;
import io.github.cantido.qlearner.agent.AgentBuilder;

/**
 * Builds an {@link Agent} and provides all fields used to build a GridWorld for testing. 
 */
public class GridWorldBuilder extends AgentBuilder {
  /**
   * Create a {@code GridWorldBuilder} with default parameters.
   */
  public GridWorldBuilder() {
    super(new GridWorldEnvironment(10, 10, 0, 0, 10, 10));
  }
}
