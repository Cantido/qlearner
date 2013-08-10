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
			
			try {
				q.timeStep();
			} catch (GoalReachedException e) {
				System.out.println(e.getMessage() + ", took " + episodeTicks + " steps.");
				q.restart();
				episodeTicks = 0;
			}
		}
	}
	
	public static final int MAX_X = 10;
	public static final int MAX_Y = 7;
	
	private final QState GOAL_STATE;
	private final QState START_STATE;
	
	private double EPSILON = 0.1;
	private double LEARNING_RATE = 1;
	private double DISCOUNT_FACTOR = 1;
	
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
	private void timeStep() throws GoalReachedException {
		
		QAction actionToTake;
		
		if (Math.random() < EPSILON) {
			actionToTake = QAction
					.getActionFromOrdinal((int) ((Math.random() * 4)));
			
		} else {
			actionToTake = getBestAction(currentState);
			
		}
		
		updateQ(currentState, actionToTake);
		
		currentState.takeAction(actionToTake);

		
		if (currentState.equals(GOAL_STATE)) {
			throw new GoalReachedException();
		}
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

		int reward = -1;
		if (sPrime.equals(GOAL_STATE))
			reward = 10;
		
		double newQ = getQ(s, a) + (LEARNING_RATE * (reward + DISCOUNT_FACTOR * getBestQ(sPrime) - getQ(s, a)));
		
		q[s.getX()][s.getY()][a.ordinal()] = newQ;
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