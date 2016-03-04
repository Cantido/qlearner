/**
 * This file is part of qlearner
 *
 *  Qlearner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Qlearner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Qlearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.example.gridworld;

import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.Action;

public enum GridWorldAction implements Action {
    UP, DOWN, LEFT, RIGHT;

    @SuppressWarnings("null")
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Nullable private static GridWorldEnvironment gridWorld;

    public static void setGridWorld(GridWorldEnvironment gw) {
        gridWorld = gw;
    }

    @Override
    public void execute() {
        final GridWorldEnvironment gridWorld = GridWorldAction.gridWorld;
        if(gridWorld == null) {
            throw new IllegalStateException("Grid World Environment was not initalized yet");
        }
        
        logger.debug("Executing GridWorldAction {}", this);
        switch (this) {
            case UP:
                gridWorld.moveUp();
                break;
            case DOWN:
                gridWorld.moveDown();
                break;
            case LEFT:
                gridWorld.moveLeft();
                break;
            case RIGHT:
                gridWorld.moveRight();
                break;
            default:
                throw new IllegalStateException("GridWorldAction not equal to any direction: " + this);
        }
    }
}
