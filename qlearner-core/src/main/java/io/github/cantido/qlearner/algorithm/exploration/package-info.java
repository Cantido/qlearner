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
 * Objects supporting the exploration of the problem space.
 * <p>
 * Exploration usually means taking actions that are not the most immediately profitable with the
 * intent to escape local maxima and find global maxima.
 * </p>
 */
@javax.annotation.ParametersAreNonnullByDefault
package io.github.cantido.qlearner.algorithm.exploration;
