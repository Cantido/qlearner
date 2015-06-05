package qlearning.domain;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import org.apache.commons.lang3.Validate;

public class ExplorationFactor {
    private final Double value;
    
    public ExplorationFactor(double value) {
        validateFactorValue(value);
        this.value = value;
    }
    
    private void validateFactorValue(Double factorValue) {
        Validate.inclusiveBetween(DOUBLE_ZERO, DOUBLE_ONE, factorValue, "Exploration factor must be within [0,1]");
    }
    
    public Double getValue() {
        return value;
    }
    
    public boolean shouldExplore(Double checkValue) {
        validateCheckValue(checkValue);
        if (value.equals(DOUBLE_ZERO)) return false;
        if (value.equals(DOUBLE_ONE)) return true;
        return checkValue < value;
    }
    
    private void validateCheckValue(Double checkValue) {
        Validate.isTrue(!DOUBLE_ONE.equals(checkValue), "Test value must be within [0,1). Given value: %f", checkValue);
        Validate.inclusiveBetween(DOUBLE_ZERO, DOUBLE_ONE, checkValue, "Test value must be within [0,1). Given value: %f", checkValue);
    }
    
    @Override
    public String toString() {
        return String.format("ExplorationFactor[%f]", value);
    }
}
