package qlearning.domain.quality;

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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import qlearning.domain.learning.DiscountFactor;
import qlearning.domain.learning.LearningRate;
import qlearning.domain.learning.Reward;

public class BackwardInductionTest {
	@SuppressWarnings("null")
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
	
	private final QualityUpdateStrategy backwardInduction = new BackwardInduction();
	
    @Test
    public void computeNewQuality() {
        Quality oldQuality = new Quality(1.0);
        LearningRate learningRate = new LearningRate(1.0);
        Reward reward = new Reward(1.0);
        DiscountFactor discountFactor= new DiscountFactor(1);
        Quality optimalFutureValueEstimate = new Quality(1.0);
        
        Quality expected = new Quality(2.0);
        Quality actual = backwardInduction.next(oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);
        
        assertThat(actual, is(expected));
    }
}
