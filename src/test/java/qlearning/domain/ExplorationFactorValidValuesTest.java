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
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.Theories;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import qlearning.domain.ExplorationFactor;

@RunWith(Theories.class)
public class ExplorationFactorValidValuesTest {
    @DataPoints
    public static double[] values = new double[] {
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
    
    @Theory
    public void explorationFactorBetweenZeroAndOneInclusiveIsLegal(double value) {
        assumeThat(value, greaterThanOrEqualTo(DOUBLE_ZERO));
        assumeThat(value, lessThanOrEqualTo(DOUBLE_ONE));

        new ExplorationFactor(value);

        // Expect no exceptions
    }

    @Theory
    public void explorationFactorBelowZeroIsIllegal(double value) {
        assumeThat(value, lessThan(DOUBLE_ZERO));

        exception.expect(IllegalArgumentException.class);
        
        new ExplorationFactor(value);
    }

    @Theory
    public void explorationFactorAboveOneIsIllegal(double value) {
        assumeThat(value, greaterThan(DOUBLE_ONE));

        exception.expect(IllegalArgumentException.class);
        
        new ExplorationFactor(value);
    }
}
