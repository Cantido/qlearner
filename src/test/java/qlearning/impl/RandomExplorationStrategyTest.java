package qlearning.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import qlearning.Action;
import qlearning.State;
import qlearning.domain.ExplorationFactor;
import qlearning.domain.Quality;
import qlearning.domain.StateActionQuality;

public class RandomExplorationStrategyTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    @InjectMocks RandomExplorationStrategy strategy;
    
    @Mock ExplorationFactor explorationFactor;
    @Mock Random random;
    
    @Mock State state;
    @Mock Action goodAction;
    @Mock Action badAction;
    
    Quality goodQuality;
    Quality badQuality;
    
    StateActionQuality goodTriplet;
    StateActionQuality badTriplet;
    
    Collection<StateActionQuality> stateActionQualities;
    
    
    @Before
    public void setUp() {
        goodQuality = new Quality(1.0);
        badQuality = new Quality(0.0);
        
        goodTriplet = new StateActionQuality(state, goodAction, goodQuality);
        badTriplet = new StateActionQuality(state, badAction, badQuality);
        
        stateActionQualities = new ArrayList<>();
        stateActionQualities.add(goodTriplet);
        stateActionQualities.add(badTriplet);
    }
    
    @Test
    public void getBestAction() {
        when(explorationFactor.shouldExplore(anyDouble())).thenReturn(false);
        
        Action actualAction = strategy.getNextAction(stateActionQualities);
        
        assertThat(actualAction, is(goodAction));
    }

    @Test
    public void getRandomAction() {
        when(explorationFactor.shouldExplore(anyDouble())).thenReturn(true);
        
        Action actualAction = strategy.getNextAction(stateActionQualities);
        
        assertThat(actualAction, isOneOf(goodAction, badAction));
    }
}
