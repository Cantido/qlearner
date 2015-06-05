package qlearning.domain;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assume.assumeThat;

import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import qlearning.domain.DiscountFactor;

@RunWith(Theories.class)
public class DiscountFactorTest {

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

    @Theory
    public void discountFactorBelowZeroIsIllegal(double discountFactor) {
        assumeThat(discountFactor, lessThan(DOUBLE_ZERO));

        exception.expect(IllegalArgumentException.class);
        new DiscountFactor(discountFactor);
    }

    @Theory
    public void discountFactorAboveZeroIsLegal(double discountFactor) {
        assumeThat(discountFactor, greaterThan(DOUBLE_ZERO));

        new DiscountFactor(discountFactor);

        // Expect no exceptions
    }
}
