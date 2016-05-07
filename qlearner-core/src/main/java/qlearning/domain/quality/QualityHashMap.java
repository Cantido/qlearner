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

package qlearning.domain.quality;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.ImmutablePair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qlearning.client.Action;
import qlearning.client.State;
import qlearning.domain.quality.Quality;
import qlearning.domain.quality.QualityMap;

public class QualityHashMap implements QualityMap {
    @SuppressWarnings("null")
	@Nonnull private static final Logger logger = LoggerFactory.getLogger(QualityHashMap.class);
    @Nonnegative private final int EXPECTED_AVERAGE_ACTIONS_PER_STATE;
    
    /**
     * Mapping of State-Action pairs to their Quality value
     */
    private final Map<ImmutablePair<State, Action>, Quality> actionQualities;
    /**
     * An optimization; stores the best quality for each state
     * 
     * Be careful if you are changing this data structure: it is possible
     * to have two equivalent quality values for a given state,
     * and then when we update any of the gridworld.actions with that quality,
     * we would lose that quality for all gridworld.actions.
     */
    @Nonnull private final Map<State, PriorityQueue<Quality>> bestQualities;
	@SuppressWarnings("null")
	@Nonnull private Quality defaultQuality = Quality.ZERO;
    
    public QualityHashMap() {
        // Will just match the default PriorityQueue size
        EXPECTED_AVERAGE_ACTIONS_PER_STATE = 11;
        actionQualities = new HashMap<>();
        bestQualities = new HashMap<>();
    }
    
    public QualityHashMap(@Nonnegative int expectedStates, @Nonnegative int actionsPerState) {
    	if(expectedStates < 0) throw new IllegalArgumentException("Was given a negative expectedStates number, which is invalid.");
    	if(actionsPerState < 0) throw new IllegalArgumentException("Was given a negative actionsPerState number, which is invalid.");
    	
        EXPECTED_AVERAGE_ACTIONS_PER_STATE = actionsPerState;
        actionQualities = new HashMap<>(expectedStates * actionsPerState);
        bestQualities = new HashMap<>(expectedStates);
    }
    
    public void setDefaultQuality(Quality defaultQuality) {
        this.defaultQuality = defaultQuality;
    }
    
    @Override
    public Quality getDefaultQuality() {
        return this.defaultQuality;
    }
    
    @Override
    public void put(State state, Action action, Quality quality) {
        Quality oldQuality = get(state, action);
        
        actionQualities.put(new ImmutablePair<>(state, action), quality);
        
        PriorityQueue<Quality> queueToUpdate;
        
        if(bestQualities.containsKey(state)) {
            queueToUpdate = bestQualities.get(state);
            queueToUpdate.remove(oldQuality);
        } else {
            queueToUpdate = new PriorityQueue<>(EXPECTED_AVERAGE_ACTIONS_PER_STATE, Quality.DESCENDING_ORDER);
            bestQualities.put(state, queueToUpdate);
        }

        logger.debug("Updating quality for [{}, {}] to {}", state, action, quality);
        
        queueToUpdate.add(quality);
    }

    @Override
    public Quality get(State state, Action action) {
        Quality quality = actionQualities.get(new ImmutablePair<>(state, action));
        
        if(quality == null) {
            return defaultQuality;
        }
        
        return quality;
    }
    
    @Override
    public Quality getBestQuality(State state) {
    	Quality bestQuality = defaultQuality;
    	
        if(bestQualities.containsKey(state)) {
        	bestQuality = bestQualities.get(state).peek();
        }
        if(bestQuality == null) {
        	return defaultQuality;
        }
        
        return bestQuality;
    }
    
    @Override
    public StateActionQuality getTriplet(State state, Action action) {
        return new StateActionQuality(state, action, this.get(state, action));
    }
}
