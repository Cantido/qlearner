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

package qlearning;

import java.util.Collection;

import qlearning.domain.Quality;
import qlearning.domain.StateActionQuality;

/**
 * The algorithm that is used to determine the next {@link Action} to take.
 */
public interface ExplorationStrategy {
    /**
     * Pick the next {@link Action} to take
     * 
     * @param stateActionQualities {@link State}-{@link Action} pairs and their associated {@link Quality Qualities}
     * @return the chosen action
     */
    public Action getNextAction(Collection<StateActionQuality> stateActionQualities);
}
