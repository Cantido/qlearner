package qlearning;

public interface QualityMap {
    public static final double MIN_QUALITY = Double.NEGATIVE_INFINITY;
    
    public void put(State state, Action action, double quality);
    public double get(State state, Action action);
}
