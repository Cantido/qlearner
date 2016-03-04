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

package qlearning.impl;

import static org.junit.Assert.*;
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
    
    Set<Action> actions = new HashSet<>();
    
    @Before
    public void setUp() {
        actions.add(worstAction);
        actions.add(middleAction);
        actions.add(highestAction);
        
        map.put(state, highestAction, lowestQuality);
        map.put(state, middleAction, middleQuality);
        map.put(state, highestAction, highestQuality);
    }

    @Test
    public void testPutValueCanBeRetrieved() {
        Quality gotQuality = map.get(state, highestAction);
        
        assertThat(gotQuality, is(highestQuality));
    }
    
    @Test
    public void getBestQualityForState() {
        Quality actualQuality = map.getBestQuality(state, actions);
        
        assertThat(actualQuality, is(highestQuality));
    }
}
