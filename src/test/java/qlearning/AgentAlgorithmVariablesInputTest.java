package qlearning;

import static org.junit.Assume.*;
import static org.hamcrest.Matchers.*;
import static org.apache.commons.lang3.math.NumberUtils.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * Tests for the {@link Agent}'s setters for variables which have an influence on the Q-learning algorithm.
 */
@RunWith(Theories.class)
public class AgentAlgorithmVariablesInputTest {
    
    Agent agent;
    
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
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        agent = new Agent();
    }

    @Theory
    public void learningRateBetweenZeroAndOneInclusiveIsLegal(double learningRate) {
        assumeThat(learningRate, greaterThanOrEqualTo(DOUBLE_ZERO));
        assumeThat(learningRate, lessThanOrEqualTo(DOUBLE_ONE));

        agent.setLearningRate(learningRate);

        // Expect no exceptions
    }

    @Theory
    public void learningRateLessThanZeroIsIllegal(double learningRate) {
        assumeThat(learningRate, lessThan(DOUBLE_ZERO));

        exception.expect(IllegalArgumentException.class);
        agent.setLearningRate(learningRate);
    }

    @Theory
    public void learningRateGreaterThanOneIsIllegal(double learningRate) {
        assumeThat(learningRate, greaterThan(DOUBLE_ONE));

        exception.expect(IllegalArgumentException.class);
        agent.setLearningRate(learningRate);
    }
}
