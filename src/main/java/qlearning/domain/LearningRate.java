package qlearning.domain;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import org.apache.commons.lang3.Validate;

/**
 * Determines to what extent the newly acquired information will override the old information.
 * <p>
 * A factor of 0 will make the agent not learn anything, while a factor of 1 would make the agent consider only the
 * most recent information. In fully deterministic environments, a learning rate of 1 is optimal. When the problem
 * is stochastic, the algorithm still converges under some technical conditions on the learning rate, that require
 * it to decrease to zero.
 * </p>
 * <p>
 * The default learning rate is 1.
 * </p>
 */
public class LearningRate {
    private final double value;
    
   /**
    * Create a new {@code LearningRate} with the given value.
    * <p>
    * A factor of 0 will make the agent not learn anything, while a factor of 1 would make the agent consider only the
    * most recent information. In fully deterministic environments, a learning rate of 1 is optimal. When the problem
    * is stochastic, the algorithm still converges under some technical conditions on the learning rate, that require
    * it to decrease to zero.
    * </p>
    * <p>
    * The default learning rate is 1.
    * </p>
    * 
    * @param value the value of the new learning rate, in the range [0, 1]
    */
    public LearningRate(double value) {
        Validate.inclusiveBetween(DOUBLE_ZERO, DOUBLE_ONE, value,
                "Learning rate must be between zero and one (inclusive)");
        this.value = value;
    }
    
    public double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.format("LearningRate[%f]", value);
    }
}
