/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QLearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.domain.exploration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import qlearning.client.Action;
import qlearning.client.State;
import qlearning.domain.quality.Quality;
import qlearning.domain.quality.StateActionQuality;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

@SuppressWarnings({ "null", "javadoc" })
public class RandomExplorationStrategyTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    
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
    	RandomExplorationStrategy strategy
    		= new RandomExplorationStrategy(ExplorationFactor.NEVER_EXPLORE, random);
        
        Action actualAction = strategy.getNextAction(stateActionQualities);
        
        assertThat(actualAction, is(goodAction));
    }

    @Test
    public void getRandomAction() {
    	RandomExplorationStrategy strategy
    		= new RandomExplorationStrategy(ExplorationFactor.ALWAYS_EXPLORE, random);
        
        Action actualAction = strategy.getNextAction(stateActionQualities);
        
        assertThat(actualAction, isOneOf(goodAction, badAction));
    }
}
