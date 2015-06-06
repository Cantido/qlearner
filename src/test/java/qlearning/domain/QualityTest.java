package qlearning.domain;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import qlearning.domain.Quality;

public class QualityTest {

    Quality lower = new Quality(0.0);
    Quality higher = new Quality(1.0);

    @Test
    public void compareLessThan() {
        assertThat(lower.compareTo(higher), lessThan(0));
    }

    @Test
    public void compareHigherThan() {
        assertThat(higher.compareTo(lower), greaterThan(0));
    }
    
    @Test
    public void compareEquals() {
        Quality first = new Quality(0.0);
        Quality second = new Quality(0.0);
        assertThat(first.compareTo(second), is(0));
    }
    
    @Test
    public void computeNewQuality() {
        Quality oldQuality = new Quality(1.0);
        LearningRate learningRate = new LearningRate(1.0);
        Reward reward = new Reward(1.0);
        DiscountFactor discountFactor= new DiscountFactor(1);
        Quality optimalFutureValueEstimate = new Quality(1.0);
        
        Quality expected = new Quality(2.0);
        Quality actual = new Quality(oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);
        
        assertThat(actual, is(expected));
    }
}
