package qlearning;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.Collections;

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
    @Mock Action goodAction;
    @Mock Action badAction;
    Quality badQuality = new Quality(-1.0);
    Quality goodQuality = new Quality(1.0);
    
    @Before
    public void setUp() {
        map = new QualityHashMap();
    }

    @Test
    public void testPutValueCanBeRetrieved() {
        map.put(state, goodAction, goodQuality);
        map.put(state, badAction, badQuality);
        Quality gotQuality = map.get(state, goodAction);
        
        assertThat(gotQuality, is(goodQuality));
    }
}
