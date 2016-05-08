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

package qlearning.domain;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assume.assumeThat;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import qlearning.domain.exploration.ExplorationFactor;

@SuppressWarnings({ "null", "javadoc" })
@RunWith(Theories.class)
public class ExplorationFactorShouldExploreTheoryTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException exception = ExpectedException.none();
    
    @DataPoints("invalidCheckValues")
    public static double[] invalidCheckValues = new double[] {
        DOUBLE_ONE
    };
    
    
    @DataPoints("checkValues")
    public static double[] checkValues = new double[] {
        DOUBLE_ZERO,
        0.5
    };
    
    @DataPoints("factorValues")
    public static double[] factorValues = new double[] {
        DOUBLE_ZERO,
        0.5,
        DOUBLE_ONE
    };
    
    @Theory 
    public void zeroFactorNeverExplores(@FromDataPoints("checkValues") double checkValue,
                                        @FromDataPoints("factorValues") double factorValue) {
        assumeThat(factorValue, equalTo(DOUBLE_ZERO));
        
        ExplorationFactor factor = new ExplorationFactor(factorValue);
       
        assertThat(factor.shouldExplore(checkValue), is(false));
    }
    
    @Theory
    public void oneFactorAlwaysExplores(@FromDataPoints("checkValues") double checkValue,
                                        @FromDataPoints("factorValues") double factorValue) {
        assumeThat(factorValue, equalTo(DOUBLE_ONE));
        
        ExplorationFactor factor = new ExplorationFactor(factorValue);
        
        assertThat(factor.shouldExplore(checkValue), is(true));
    }
    
    @Theory
    public void generatedLessThanExplores(@FromDataPoints("checkValues") double checkValue,
                                          @FromDataPoints("factorValues") double factorValue) {
        assumeThat(checkValue, lessThan(factorValue));
        assumeThat(factorValue, not(DOUBLE_ZERO));
        
        ExplorationFactor factor = new ExplorationFactor(factorValue);
        
        assertThat(factor.shouldExplore(checkValue), is(true));
    }
    
    @Theory
    public void generatedGreaterThanEqualToDoesNotExplore(@FromDataPoints("checkValues") double checkValue,
                                                          @FromDataPoints("factorValues") double factorValue) {
        assumeThat(checkValue, greaterThanOrEqualTo(factorValue));
        assumeThat(factorValue, not(DOUBLE_ONE));
        
        ExplorationFactor factor = new ExplorationFactor(factorValue);
        
        assertThat(factor.shouldExplore(checkValue), is(false));
    }
    
    @Theory
    public void generatedEqualToDoesNotExplore(@FromDataPoints("checkValues") double checkValue,
                                               @FromDataPoints("factorValues") double factorValue) {
        assumeThat(checkValue, equalTo(factorValue));
        assumeThat(factorValue, not(DOUBLE_ONE));
        
        ExplorationFactor factor = new ExplorationFactor(factorValue);
        
        assertThat(factor.shouldExplore(checkValue), is(false));
    }
    
    @Theory
    public void checkValueOfOneIsInvalid(@FromDataPoints("invalidCheckValues") double checkValue,
                                         @FromDataPoints("factorValues") double factorValue) {
        assumeThat(checkValue, equalTo(DOUBLE_ONE));
        
        ExplorationFactor factor = new ExplorationFactor(factorValue);
        
        exception.expect(IllegalArgumentException.class);
        factor.shouldExplore(checkValue);
    }

}
