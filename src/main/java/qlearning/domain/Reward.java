package qlearning.domain;

public class Reward {
    private final Double value;
    
    public Reward(double value) {
        this.value = value;
    }
    
    public Double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
