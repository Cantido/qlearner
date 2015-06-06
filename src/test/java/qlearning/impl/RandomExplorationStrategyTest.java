package qlearning.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
    
    Pair<State, Action> goodPair;
    Pair<State, Action> badPair;
    
    Map<Pair<State, Action>, Quality> stateActionQualities;
    
    
    @Before
    public void setUp() {
        goodQuality = new Quality(1.0);
        badQuality = new Quality(0.0);
        
        goodPair = ImmutablePair.of(state, goodAction);
        badPair = ImmutablePair.of(state, badAction);
        
        stateActionQualities = new HashMap<>();
        stateActionQualities.put(goodPair, goodQuality);
        stateActionQualities.put(badPair, badQuality);
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
