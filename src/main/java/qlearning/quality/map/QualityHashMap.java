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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.ImmutablePair;

import qlearning.Action;
import qlearning.State;
import qlearning.quality.Quality;

public class QualityHashMap implements QualityMap {
    private Map<ImmutablePair<State, Action>, Quality> qualities = new HashMap<>();
    
    private Quality defaultQuality = new Quality(0.0);
    
    public void setDefaultQuality(Quality defaultQuality) {
        this.defaultQuality = defaultQuality;
    }
    
    public Quality getDefaultQuality() {
        return this.defaultQuality;
    }
    
    @Override
    public void put(State state, Action action, Quality quality) {
        qualities.put(new ImmutablePair<>(state, action), quality);
    }

    @Override
    public Quality get(State state, Action action) {
        Quality qualityToGet;
        ImmutablePair<State, Action> pair = new ImmutablePair<>(state, action);

        if (qualities.containsKey(pair)) {
            qualityToGet = qualities.get(pair);

        } else {
            qualityToGet = defaultQuality;
        }

        return qualityToGet;
    }
    
    @Override
    public Quality getBestQuality(State state, Set<Action> actions) {
        TreeSet<Quality> qualities = new TreeSet<>();
        
        for (Action action : actions) {
            qualities.add(get(state, action));
        }
        return qualities.last();
    }
}