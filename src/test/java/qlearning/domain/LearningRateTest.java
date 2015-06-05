package qlearning.domain;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assume.assumeThat;

import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import qlearning.domain.LearningRate;

@RunWith(Theories.class)
public class LearningRateTest {
    @Rule public ExpectedException exception = ExpectedException.none();

    @DataPoints
    public static double[] doubles = new double[] {
        Double.NEGATIVE_INFINITY,
        (- Double.MIN_NORMAL),
        DOUBLE_ZERO,
        Double.MIN_NORMAL,
        (DOUBLE_ONE - Double.MIN_NORMAL),
        DOUBLE_ONE,
        (DOUBLE_ONE + Double.MIN_NORMAL),
        Double.POSITIVE_INFINITY
    };
    
    @Theory
    public void learningRateBetweenZeroAndOneInclusiveIsLegal(double learningRate) {
        assumeThat(learningRate, greaterThanOrEqualTo(DOUBLE_ZERO));
        assumeThat(learningRate, lessThanOrEqualTo(DOUBLE_ONE));
        
        // No exception expected
        new LearningRate(learningRate);
    }

    @Theory
    public void learningRateLessThanZeroIsIllegal(double learningRate) {
        assumeThat(learningRate, lessThan(DOUBLE_ZERO));

        exception.expect(IllegalArgumentException.class);
        new LearningRate(learningRate);
    }

    @Theory
    public void learningRateGreaterThanOneIsIllegal(double learningRate) {
        assumeThat(learningRate, greaterThan(DOUBLE_ONE));

        exception.expect(IllegalArgumentException.class);
        new LearningRate(learningRate);
    }

}
