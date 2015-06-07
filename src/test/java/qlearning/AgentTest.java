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

package qlearning;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;


public class AgentTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock private Environment environment;
    @Mock private ExplorationStrategy explorationStrategy;
    @Mock private LearningRate learningRate;
    @Mock private DiscountFactor discountFactor;
    @Mock private QualityMap qualityMap;

    @Test
    public void testNullEnvironmentIsIllegal() {
        exception.expect(NullPointerException.class);
        
        new Agent((Environment) null, explorationStrategy, learningRate, discountFactor, qualityMap);
    }
}
