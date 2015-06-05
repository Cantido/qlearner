package qlearning.domain;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The learned value of a {@link State}-{@link Action} pair's potential for future {@link Reward}s
 */
public class Quality implements Comparable<Object> {
    private final Double value;
    
    public Quality(Double value) {
        this.value = value;
    }
    
    public Quality(Quality oldQuality,
            LearningRate learningRate,
            Reward reward,
            DiscountFactor discountFactor,
            Quality optimalFutureValueEstimate) {

        Validate.notNull(oldQuality, "Quality cannot be null");
        Validate.notNull(reward, "Reward cannot be null");
        Validate.notNull(optimalFutureValueEstimate, "Optimal future value estimate cannot be null");
        Validate.notNull(learningRate, "LearningRate cannot be null");
        Validate.notNull(discountFactor, "DiscountFactor cannot be null");
        
        value = oldQuality.getValue()
                + (learningRate.getValue() *
                        (reward.getValue() + discountFactor.getValue() * optimalFutureValueEstimate.getValue() - oldQuality.getValue()));
    }
    
    public Double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(Object o) {
        Quality other = (Quality) o;
        return new CompareToBuilder()
          .append(this.getValue(), other.getValue())
          .toComparison();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 61).
                append(getValue()).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        Quality rhs = (Quality) obj;
        return new EqualsBuilder()
                      .append(getValue(), rhs.getValue())
                      .isEquals();
    }
    
}
