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

package gridworld.actions;

import gridworld.GridWorldEnvironment;
import qlearning.client.Action;

import javax.annotation.concurrent.NotThreadSafe;
/**
 * An {@link Action} to move "left" in a {@link GridWorldEnvironment}.
 */
@NotThreadSafe
public class Left extends GridWorldAction {
    /**
     * Create a {@code Left} action.
     * 
     * @param environment the environment to modify when this action is
     * executed.
     */
    public Left(GridWorldEnvironment environment) {
        super(environment);
    }
    
    @Override
    public void run() {
        environment.moveLeft();
    }
}