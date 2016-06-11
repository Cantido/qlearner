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
 * Base classes that must be implemented by this library's clients.
 * <p>
 * Implementations of the {@link io.github.cantido.qlearner.client.Environment Environment}
 * interface represent some system (real-world or otherwise) that you are trying to affect.
 * This program will use a single instance of your {@code Environment} implementation to fetch
 * data at every learning iteration. An {@code Environment} provides its current state in a
 * {@link io.github.cantido.qlearner.client.State State} object, provided by
 * {@link io.github.cantido.qlearner.client.Environment#getState() Environment.getState()}.
 * </p><p>
 * Implementations of the {@link io.github.cantido.qlearner.client.State State} interface represent
 * certain conditions of the system you are trying to affect. If your {@code Environment} has a
 * temperature property, then a {@code State} would be a temperature sensor reading. A
 * {@code State} has a {@link io.github.cantido.qlearner.algorithm.model.Reward Reward} value
 * that represents how desirable that {@code State} is. The program tries to maximize positive
 * rewards and minimize negative rewards.
 * </p><p>
 * <em>Warning</em>: This program does not yet have the functionality necessary to learn continuous
 * values. You must build your {@code State} objects to round these continuous values to the point
 * where it is likely that the program will see that value again. For instance, you could round
 * temperature readings to a lower precision. See
 * {@link io.github.cantido.qlearner.algorithm.quality.QualityHashMap QualityHashMap} for more
 * information.
 * </p><p>
 *  Implementations of the {@link io.github.cantido.qlearner.client.Action Action} interface
 *  represent a thing that can be done to affect the {@code Environment}. This program tries to
 *  learn the best {@code Action} to take for a given {@code State}. Each call to
 *  {@link io.github.cantido.qlearner.agent.Agent#takeNextAction() Agent.takeNextAction()} will
 *  call {@link io.github.cantido.qlearner.client.Action#run() Action.run()} once.
 * </p>
 */
@javax.annotation.ParametersAreNonnullByDefault
package io.github.cantido.qlearner.client;
