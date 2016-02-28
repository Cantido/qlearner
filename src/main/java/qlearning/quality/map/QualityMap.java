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

package qlearning.quality.map;

import java.util.Set;

import qlearning.Action;
import qlearning.State;
import qlearning.quality.Quality;

public interface QualityMap {
    public static final Quality MIN_QUALITY = new Quality(Double.NEGATIVE_INFINITY);
    
    public void put(State state, Action action, Quality quality);
    public Quality get(State state, Action action);
    public Quality getBestQuality(State state, Set<Action> actions);
}
