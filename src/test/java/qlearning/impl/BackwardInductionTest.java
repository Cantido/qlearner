package qlearning.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;
import qlearning.domain.Reward;
import qlearning.quality.Quality;
import qlearning.quality.strategy.BackwardInduction;
import qlearning.quality.strategy.QualityUpdateStrategy;

public class BackwardInductionTest {
	@Rule public MockitoRule mockito = MockitoJUnit.rule();
	
	private QualityUpdateStrategy backwardInduction;
	
	@Before
	public void setUp() {
		backwardInduction = new BackwardInduction();
	}
	
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
