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
	
	public static final int MAX_X = 10;
	public static final int MAX_Y = 7;
	
	private final QState GOAL_STATE;
	private final QState START_STATE;
	
	private final double EXPLORATION_FACTOR = 0.1;
	private final double LEARNING_RATE = 1;
	private final double DISCOUNT_FACTOR = 1;
	
	private double[][][] q = new double[MAX_X][MAX_Y][4];
	
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
		QAction actionToTake;
		
		if (shouldExplore()) {
			actionToTake = getRandomAction();
		} else {
			actionToTake = getBestAction();
		}
		
		takeAction(actionToTake);
	}
	
	/**
	 * @return whether or not the agent should explore in this time step
	 */
	private boolean shouldExplore() {
		return (Math.random() < EXPLORATION_FACTOR);
	}
	
	/**
	 * @return a random action
	 */
	@SuppressWarnings("static-method")
	private QAction getRandomAction() {
		return QAction.fromOrdinal((int) ((Math.random() * 4)));
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
		
		double newQ = getQ(s, a) + (LEARNING_RATE * (sPrime.getReward() + DISCOUNT_FACTOR * getBestQ(sPrime) - getQ(s, a)));
		
		q[s.getX()][s.getY()][a.ordinal()] = newQ;
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
	
	
	/**
	 * Returns the {@link QAction} with the highest Q-value for the given {@link QState}
	 * 
	 * @param s The state for which to find the best action
	 * @return The action with the highest q-value
	 */
	private QAction getBestAction(QState s) {
		QAction bestAction = QAction.UP;

		for (QAction action : QAction.values()) {
			if (getQ(s, action) > getQ(s, bestAction)) {
				bestAction = action;
			} else if ((getQ(s, action) == getQ(s, bestAction)) && Math.random() > 0.5) {
				bestAction = action;
			}
		}
		return bestAction;
	}
	
	/**
	 * @return the best action for the current state
	 */
	private QAction getBestAction() {
		return getBestAction(currentState);
	}
	
	/**
	 * Returns the highest q-value for the given state
	 * 
	 * @param s The state for find the best q-value for
	 * @return The best q-value for the state
	 */
	private double getBestQ(QState s) {
		double bestQ = -(Double.MAX_VALUE);
		for (QAction action : QAction.values()) {
			if (getQ(s, action) > bestQ)
				bestQ = getQ(s, action);
		}
		return bestQ;
	}
	
	/**
	 * Returns the q-value for the given state-action pair
	 * 
	 * @param s The state
	 * @param a The action
	 * @return The q-value for a given state-action pair
	 */
	private double getQ (QState s, QAction a) {
		//assert(this.isValidState(s));
		return q[s.getX()][s.getY()][a.ordinal()];
	}
}