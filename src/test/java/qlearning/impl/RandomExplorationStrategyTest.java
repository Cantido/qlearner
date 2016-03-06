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
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.jdt.annotation.NonNullByDefault;
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
import qlearning.domain.StateActionQuality;
import qlearning.quality.Quality;

@SuppressWarnings("null")
@NonNullByDefault({})
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
    
    SortedSet<StateActionQuality> stateActionQualities;
    
    
    @Before
    public void setUp() {
        goodQuality = new Quality(1.0);
        badQuality = new Quality(0.0);
        
        goodTriplet = new StateActionQuality(state, goodAction, goodQuality);
        badTriplet = new StateActionQuality(state, badAction, badQuality);
        
        stateActionQualities = new TreeSet<>();
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
