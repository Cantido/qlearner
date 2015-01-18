package qlearning;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import qlearning.impl.QualityHashMap;

@RunWith(MockitoJUnitRunner.class)
public class QualityHashMapTest {
    
    QualityMap map;
    @Mock State state;
    @Mock Action action;
    double quality = 1.0;
    
    @Before
    public void setUp() {
        map = new QualityHashMap();
    }

    @Test
    public void testPutValueCanBeRetrieved() {
        map.put(state, action, quality);
        double gotQuality = map.get(state, action);
        
        assertThat(gotQuality, is(quality));
    }
}
