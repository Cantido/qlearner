package qlearning;


/**
 * Main class of the qlearning package
 * 
 * @author Robert Richter
 */
public class QLearner {
	public static void main(String[] args) {
		QState start = new QState(0, 3);
		QState goal = new QState(7, 3);
		
		QLearner q = new QLearner(start, goal);
		
		int episodeTicks = 0;
		
		for (int i = 0; i < 10000; i++, episodeTicks++) {
			q.timeStep();
			if(q.hasReachedGoal()) {
				System.out.println("Goal has been reached, took " + episodeTicks + " steps.");
				q.restart();
				episodeTicks = 0;
			}
		}
	}
	
	private final QState GOAL_STATE;
	private final QState START_STATE;
	
	private final double EXPLORATION_FACTOR = 0.1;
	private final double LEARNING_RATE = 1;
	private final double DISCOUNT_FACTOR = 1;
	

	
	private QState currentState;
	
	public QLearner() {
		this(new QState(0, 3), new QState(7, 3));
	}
	
	public QLearner(QState startState, QState goalState) {
		START_STATE = new QState(startState);
		GOAL_STATE = new QState(goalState);
		currentState = new QState(startState);
	}
	
	public boolean hasReachedGoal() {
		return (currentState.equals(GOAL_STATE));
	}
	
	/**
	 * Moves the current state back to the start state
	 */
	public void restart() {
		currentState = new QState(START_STATE);
	}
	
	/**
	 * Moves the algorithm through one full movement step
	 * 
	 * @throws GoalReachedException if the goal is reached
	 */
	private void timeStep() {
		takeAction(getNextAction());
	}
	
	public QAction getNextAction() {
		QAction nextAction;
		if (shouldExplore()) {
			nextAction = currentState.getRandomAction();
		} else {
			nextAction = currentState.getBestAction();
		}
		return nextAction;
	}
	
	/**
	 * @return whether or not the agent should explore in this time step
	 */
	private boolean shouldExplore() {
		return (Math.random() < EXPLORATION_FACTOR);
	}
	
	/**
	 * Takes an action
	 */
	private void takeAction(QAction action) {
		updateQ(currentState, action);
		currentState.takeAction(action);
	}
	
	/**
	 * Updates the Q-value for a state-action pair
	 * 
	 * @param s The State
	 * @param a The Action
	 */
	private void updateQ(QState s, QAction a) {
		QState sPrime = new QState(s);
		sPrime.takeAction(a);
		
		determineReward(sPrime);
		
		double newQ = s.getQ(a) + (LEARNING_RATE * (sPrime.getReward() + DISCOUNT_FACTOR * sPrime.getBestQ() - s.getQ(a)));
		
		currentState.setQValueForAction(newQ, a);
	}
	
	/**
	 * Sets the state's reward based on its position
	 */
	private void determineReward(QState state) {
		if (state.equals(GOAL_STATE)) {
			state.setReward(10);
		} else {
			state.setReward(-1);
		}
	}
}