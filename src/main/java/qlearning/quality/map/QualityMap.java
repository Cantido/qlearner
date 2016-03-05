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

import java.util.Set;

import qlearning.Action;
import qlearning.State;
import qlearning.quality.Quality;

@SuppressWarnings("unused")
public interface QualityMap {
    public static final QualityMap BLACK_HOLE = new QualityMap() { /* Use default behavior */ };
    
    public default void put(State state, Action action, Quality quality) {
        /* Do nothing */
    }
    public default Quality get(State state, Action action) {
        return Quality.ZERO;
    }
    
    public default Quality getBestQuality(State state) {
        return Quality.ZERO;
    }
}
