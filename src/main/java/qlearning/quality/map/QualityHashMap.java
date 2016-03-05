package qlearning.quality.map;

/*
 * #%L
 * QLearner
 * %%
 * Copyright (C) 2012 - 2016 Robert Richter
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.ImmutablePair;

import qlearning.State;
import qlearning.quality.Quality;

public class QualityHashMap implements QualityMap {
    private final Map<ImmutablePair<State, Runnable>, Quality> qualities;
    private Quality defaultQuality = Quality.ZERO;
    
    public QualityHashMap() {
        qualities = new HashMap<>();
    }
    
    public QualityHashMap(int intialCapacity) {
        qualities = new HashMap<>(intialCapacity);
    }
    
    public QualityHashMap(int intialCapacity, float loadFactor) {
        qualities = new HashMap<>(intialCapacity, loadFactor);
    }
    
    public void setDefaultQuality(Quality defaultQuality) {
        this.defaultQuality = defaultQuality;
    }
    
    public Quality getDefaultQuality() {
        return this.defaultQuality;
    }
    
    @Override
    public void put(State state, Runnable action, Quality quality) {
        qualities.put(new ImmutablePair<>(state, action), quality);
    }

    @Override
    public Quality get(State state, Runnable action) {
        Quality qualityToGet;
        ImmutablePair<State, Runnable> pair = new ImmutablePair<>(state, action);

        if (qualities.containsKey(pair)) {
            qualityToGet = qualities.get(pair);

        } else {
            qualityToGet = defaultQuality;
        }

        return qualityToGet;
    }
    
    @Override
    public Quality getBestQuality(State state) {
        TreeSet<Quality> qualities = new TreeSet<>();
        
        for (Runnable action : state.getActions()) {
            qualities.add(get(state, action));
        }
        
        return qualities.last();
    }
}
