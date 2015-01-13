package qlearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performs {@link Action}s that will lead to changes in the
 * {@link Environment}'s current {@link State}.
 */
public class Agent {
	private static final double DEFAULT_STATE_ACTION_QUALITY = NumberUtils.DOUBLE_ZERO;
	
	private double discountFactor = NumberUtils.DOUBLE_ONE;
	private double explorationFactor = 0.1;
	private double learningRate = NumberUtils.DOUBLE_ONE;

	private Environment environment;
	
	private Map<ImmutablePair<State, Action>, Double> qualities = new HashMap<>();
	
	private State previousState;
	private Action previousAction;
	
	private State currentState;

	Logger logger = LoggerFactory.getLogger("qlearner.Agent");
	
	public void setEnvironment(Environment env) {
		this.environment = env;
	}
	
	public void setLearningRate(double lr) {
		this.learningRate = lr;
	}
	
	public void setExplorationFactor(double ef) {
		this.explorationFactor = ef;
	}
	
	public void setDiscountFactor(double df) {
		this.discountFactor = df;
	}
	public void resetState() {
		this.previousState = null;
		this.previousAction = null;
		this.currentState = null;
	}
	
	public void takeNextAction() { logger.debug("---- NEW TICK --- entered takeNextAction");
		
		Validate.notNull(this.environment, "Cannot take an action without an environment.");
		
		currentState = environment.getState(); logger.debug("Got current state: {}", currentState);
		
		Action nextAction = getNextAction();
		
		updateQuality();
		
		nextAction.execute();
		
		this.previousAction = nextAction; logger.debug("Set previous action to {}", nextAction);
		this.previousState = currentState; logger.debug("Set previous state to {}", currentState);

		logger.debug("---- END OF TICK ---- exited takeNextAction");
	}
	
	private Action getNextAction() {
		Set<Action> possibleActions = currentState.getActions();
		Action nextAction;
		
		if(shouldExplore()) {
			nextAction = getRandomAction(currentState, possibleActions); logger.debug("Took random action: {}", nextAction);
		} else {
			nextAction = getBestAction(currentState, possibleActions); logger.debug("Took best action: {}", nextAction);
		}
		
		return nextAction;
	}
	
	private boolean shouldExplore() {
		return (explorationFactor > 0) && (Math.random() < explorationFactor);
	}
	
	private Action getBestAction(State state, Set<Action> possibleActions) {
		logger.debug("Determining best action from possible actions: {}", possibleActions);
		
		SortedSet<ImmutablePair<Double, Action>> actionQualities = new TreeSet<>();
		
		for(Action action : possibleActions) {
			double actionQuality = getQuality(state, action); logger.debug("Got quality for action {}: {}", action, actionQuality);
			actionQualities.add(ImmutablePair.of(actionQuality, action));
		}

		logger.debug("Possible actions and qualities: {}", actionQualities);
		
		return actionQualities.last().getRight();
	}
	
	private Action getRandomAction(State state, Set<Action> possibleActions) {
		Validate.notNull(state);
		
		List<Action> actions = new ArrayList<>(possibleActions);
		Validate.isTrue(!actions.isEmpty());
		
		return actions.get(RandomUtils.nextInt(0, actions.size()));
	}
	
	/**
	 * Update the quality of the previous state-action pair, given the desirability of the new state
	 * 
	 * @param nextState
	 */
	private void updateQuality() {
		Validate.notNull(currentState);
		
		if(this.previousState == null || this.previousAction == null) {
			logger.debug("previous state or previous actions is null");
		} else {
			double oldQuality = getQuality(this.previousState, this.previousAction);
			double reward = currentState.getReward();
			double optimalFutureValueEstimate = getBestQuality(currentState);
			
			logger.debug("Calculating new quality using the following values: (Qt+1: {}), (a: {}), (Rt+1: {}), (d: {}), (maxQt: {})", oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate);
			logger.debug("{} + ({} * ({} + {} * {} - {}))", oldQuality, learningRate, reward, discountFactor, optimalFutureValueEstimate, oldQuality);
			
			double newQuality = oldQuality + (learningRate * (reward + discountFactor * optimalFutureValueEstimate - oldQuality));
			
			ImmutablePair<State, Action> pair = ImmutablePair.of(previousState, previousAction);
			
			logger.debug("Updating quality for {} to {}", pair, newQuality);
			
			qualities.put(pair, newQuality);
		}
	}
	
	private double getBestQuality(State s) {
		Validate.notNull(s);
		
		double bestQualityForState;

		Set<Action> possibleActions = s.getActions();
		
		if(possibleActions == null || possibleActions.isEmpty()) {
			bestQualityForState = DEFAULT_STATE_ACTION_QUALITY;
		} else {
			bestQualityForState = - Double.MAX_VALUE;
			for(Action action : possibleActions) {
				double qualityForState = getQuality(s, action);
				logger.debug("Potential future quality for state {}, action {}: {}", s, action, qualityForState);
				bestQualityForState = Math.max(bestQualityForState, qualityForState);
			}
		}
		
		logger.debug("Got best quality for state {}: {}", s, bestQualityForState);
		return bestQualityForState;
	}
	
	private double getQuality(State s, Action a) {
		ImmutablePair<State, Action> pair = ImmutablePair.of(s, a);
		
		return qualities.containsKey(pair) ? qualities.get(pair) : DEFAULT_STATE_ACTION_QUALITY;
	}
}
