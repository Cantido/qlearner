package qlearning.impl;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;

public class RandomExplorationStrategyTest {
    private RandomExplorationStrategy strategy;
    
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
        strategy = new RandomExplorationStrategy();
    }
    
    @Theory
    public void explorationFactorBetweenZeroAndOneInclusiveIsLegal(double explorationFactor) {
        assumeThat(explorationFactor, greaterThanOrEqualTo(DOUBLE_ZERO));
        assumeThat(explorationFactor, lessThanOrEqualTo(DOUBLE_ONE));

        strategy.setExplorationFactor(explorationFactor);

        // Expect no exceptions
    }

    @Theory
    public void explorationFactorBelowZeroIsIllegal(double explorationFactor) {
        assumeThat(explorationFactor, lessThan(DOUBLE_ZERO));

        exception.expect(IllegalArgumentException.class);
        strategy.setExplorationFactor(explorationFactor);
    }

    @Theory
    public void explorationFactorAboveOneIsIllegal(double explorationFactor) {
        assumeThat(explorationFactor, greaterThan(DOUBLE_ONE));

        exception.expect(IllegalArgumentException.class);
        strategy.setExplorationFactor(explorationFactor);
    }
}
