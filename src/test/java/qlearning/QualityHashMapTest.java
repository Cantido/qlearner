package qlearning;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import qlearning.Action;
import qlearning.QualityMap;
import qlearning.State;
import qlearning.domain.Quality;
import qlearning.impl.QualityHashMap;

@RunWith(MockitoJUnitRunner.class)
public class QualityHashMapTest {
    
    QualityMap map;
    @Mock State state;
    @Mock Action highestAction;
    @Mock Action middleAction;
    @Mock Action worstAction;
    Quality lowestQuality = new Quality(-1.0);
    Quality middleQuality = new Quality(0.0);
    Quality highestQuality = new Quality(1.0);
    
    Set<Action> actions;
    
    @Before
    public void setUp() {
        map = new QualityHashMap();
        actions = new HashSet<>();
        
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
