package qlearning.domain;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import org.apache.commons.lang3.Validate;

/**
 * The discount factor determines the importance of future rewards.
 * <p>
 * A factor of 0 will make the agent "myopic" (or short-sighted) by only considering current rewards, while a factor
 * approaching 1 will make it strive for a long-term high reward. If the discount factor meets or exceeds 1, the
 * quality values may diverge.
 * </p>
 */
public class DiscountFactor {
    private final Double value;
    
    /**
     * Create a new {@code DiscountFactor} with the given value
     * <p>
     * A factor of 0 will make the agent "myopic" (or short-sighted) by only considering current rewards, while a factor
     * approaching 1 will make it strive for a long-term high reward. If the discount factor meets or exceeds 1, the
     * quality values may diverge.
     * </p>
     * 
     * @param value the value of the new discount factor, in the range [0, &infin;)
     */
    public DiscountFactor(double value) {
        Validate.inclusiveBetween(DOUBLE_ZERO, Double.POSITIVE_INFINITY, value,
                "Discount factor must be greater than or equal to zero");
        this.value = value;
    }
    
    public Double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.format("DiscountFactor[%f]", value);
    }
}
