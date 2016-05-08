/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QLearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.client;

import qlearning.agent.Agent;

/**
 * An act that can be performed that will lead to a change in the {@link Environment}'s current {@link State}.
 * <p>
 * The {@link Agent} assumes that taking gridworld.actions using {@link Action#run()} somehow changes the state of the
 * {@link Environment}. Calling {@code execute()} on {@code Action}s is the "output" of this system. This could drive
 * motors, change an HVAC's set temperature, move a game piece, and so on. As long as this has a chance of changing what
 * we get from {@link Environment#getState()}, then the system can learn to optimize its choices.
 * </p><p>
 * Note: This class <strong>must</strong> implement {@link #hashCode()} and {@link #equals(Object)}! The system uses
 * these comparisons to look up the quality values of {@code State-Action} pairs, so if two different {@code Action}s
 * are seen as equivalent, then the system will not work correctly. For example, if you are moving a game piece on a
 * board, make sure that "up" means the same "up" everywhere!
 * </p><p>
 * You can use this class to wrap your own {@code Runnable} classes. If your {@code Runnable}s spawn new threads, be careful of the QLearning
 * system interacting with the {@link Environment} is thread-safe, lest your Runnable manipulates the Environment
 * while the Agent is trying to get the next State from it.
 * </p>
 */
public abstract class Action implements Runnable {
	/* Intentionally left blank */
}
