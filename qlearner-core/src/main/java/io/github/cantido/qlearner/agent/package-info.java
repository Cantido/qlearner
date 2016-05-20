/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with QLearner. If not,
 * see <http://www.gnu.org/licenses/>.
 */

/**
 * The {@link io.github.cantido.qlearner.agent.Agent Agent} class is the entry point to this
 * library. It has only one method: {@link io.github.cantido.qlearner.agent.Agent#takeNextAction() 
 * Agent.takeNextAction()}.
 * <pre>
 * Agent agent = ... ;
 * 
 * while(true) {
 *     agent.takeNextAction();
 * }</pre>
 * 
 * For real-world applications, you may want to allow some time for your actions to have an effect,
 * and for your {@link io.github.cantido.qlearner.client.Environment Environment} to arrive at a
 * new {@link io.github.cantido.qlearner.client.State State}:
 * <pre>
 * while(true) {
 *     agent.takeNextAction();
 *     TimeUnit.MINUTES.sleep(1);
 * }</pre>
 * If you are trying to reach a global state after which your program will exit, you will have to
 * specify your own way of identifying that. Since your {@code Environment} is responsible for its
 * own {@code State}, why not add a method to your {@code Environment}?
 * <pre>
 * MyEnvironment myEnvironment = ... ;
 * 
 * while(!myEnvironment.isAtGoalState()) {
 *     agent.takeNextAction();
 *     TimeUnit.MINUTES.sleep(1);
 * }</pre>
 * If you ever need to reset your {@code Agent} while preserving its learned behavior, call
 * {@link io.github.cantido.qlearner.agent.Agent#reset Agent.reset()}. This is important when
 * you are trying to train your agent to find a goal state. For instance, if you are trying to
 * teach your {@code Agent} how to play a game, then you will need to call {@code Agent.reset()}
 * once the game ends, before you start your next game. If you don't, then the {@code Agent} will
 * learn that it can make a move at the end of the game that will put it back at the beginning
 * of the game!
 * <pre>
 * while(isTraining) {
 *     myGameEnvironment.startGame();
 *     
 *     while(!myGameEnvironment.isAtGoalState()) {
 *         agent.takeNextAction();
 *     }
 *     // The game is over. Now, we have to reset the agent before we start the game again.
 *     
 *     agent.reset();
 *     myGameEnvironment.endGame();
 * }</pre>
 * You can also create a brand new {@code Agent} object, just be sure to save the {@link io.github.
 * cantido.qlearner.algorithm.model.QualityMap} that you created it with. The {@code QualityMap} is
 * where the {@code Agent} stores its learned values, and represents the hard work you just
 * performed.
 */
@javax.annotation.ParametersAreNonnullByDefault
package io.github.cantido.qlearner.agent;
