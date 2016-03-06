package qlearning.impl;

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import qlearning.Action;
import qlearning.State;
import qlearning.quality.Quality;
import qlearning.quality.map.QualityHashMap;
import qlearning.quality.map.QualityMap;

@SuppressWarnings("null")
@NonNullByDefault({})
@RunWith(MockitoJUnitRunner.class)
public class QualityHashMapTest {
    
    QualityMap map = new QualityHashMap();
    @Mock State state;
    @Mock Action highestAction;
    @Mock Action middleAction;
    @Mock Action worstAction;
    Quality lowestQuality = new Quality(-1.0);
    Quality middleQuality = new Quality(0.0);
    Quality highestQuality = new Quality(1.0);
    Quality defaultQuality = map.getDefaultQuality();
    
    Set<Action> actions = new HashSet<>();
    
    @Before
    public void setUp() {
        actions.add(worstAction);
        actions.add(middleAction);
        actions.add(highestAction);
        
        when(state.getActions()).thenReturn(actions);
    }
    
    public void fillMap() {
        map.put(state, highestAction, lowestQuality);
        map.put(state, middleAction, middleQuality);
        map.put(state, highestAction, highestQuality);
    }

    @Test
    public void storesQuality() {
        fillMap();
        
        Quality gotQuality = map.get(state, highestAction);
        
        assertThat(gotQuality, is(highestQuality));
    }
    
    @Test
    public void returnsDefaultQualityForBestWhenEmpty() {
        Quality actualQuality = map.getBestQuality(state);
        
        assertThat(actualQuality, is(defaultQuality));
    }
    
    @Test
    public void returnsDefaultQualityForActionWhenEmpty() {
        Quality actualQuality = map.get(state, highestAction);
        
        assertThat(actualQuality, is(defaultQuality));
    }
    
    @Test
    public void getsHighestQualityForBest() {
        fillMap();
        
        Quality actualQuality = map.getBestQuality(state);
        
        assertThat(actualQuality, is(highestQuality));
    }
    
    @Test
    public void willNotDeleteDuplicateQualities() {
        fillMap();
        
        map.put(state, highestAction, middleQuality);
        map.put(state, middleAction, middleQuality);
        
        assertThat(map.getBestQuality(state), is(middleQuality));

        map.put(state, highestAction, highestQuality);
        map.put(state, middleAction, middleQuality); 
        
        assertThat(map.getBestQuality(state), is(highestQuality));
    }
}
