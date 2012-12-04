package qlearning;


/**
 * @author Robert Richter
 * 
 *         CpE 358 Computational Intelligence
 * 
 *         Final Project: Q-learning
 * 
 *         Implements Q-learning to navigate an agent though a windy gridworld
 * 
 *         Due Wednesday, December 5th @ noon
 * 
 */

public class QLearner {
	public static void main(String[] args) {
		State start = new State(0, 3);
		State goal = new State(7, 3);
		
		QLearner q = new QLearner(start, goal);
		
		int episodeTicks = 0;
		
		for (int i = 0; i < 10000; i++, episodeTicks++) {
			
			try {
				q.timeStep();
			} catch (GoalReachedException e) {
				System.out.println(e.msg + ", took " + episodeTicks + " steps.");
				q.restart();
				episodeTicks = 0;
			}
		}
	}
	
	static final int MAX_X = 10;
	static final int MAX_Y = 7;
	
	final State GOAL_STATE;
	final State START_STATE;
	
	private double EPSILON = 0.1;
	private double LEARNING_RATE = 1;
	private double DISCOUNT_FACTOR = 1;
	
	private double[][][] q = new double[MAX_X][MAX_Y][4];
	
	private State currentState;
	
	public QLearner() {
		this(new State(0, 3), new State(7, 3));
	}
	
	public QLearner(State startState, State goalState) {
		START_STATE = new State(startState);
		GOAL_STATE = new State(goalState);
		currentState = new State(startState);
	}
	
	public void restart() {
		currentState = new State(START_STATE);
	}
	
	private void timeStep() throws GoalReachedException {
		
		Action actionToTake;
		
		if (Math.random() < EPSILON) {
			actionToTake = Action
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
	
	private void updateQ(State s, Action a) {
		State sPrime = new State(s);
		sPrime.takeAction(a);

		int reward = -1;
		if (sPrime.equals(GOAL_STATE))
			reward = 10;
		
		double newQ = getQ(s, a) + (LEARNING_RATE * (reward + DISCOUNT_FACTOR * getBestQ(sPrime) - getQ(s, a)));
		
		q[s.getX()][s.getY()][a.ordinal()] = newQ;
	}
	
	private Action getBestAction(State s) {
		Action bestAction = Action.UP;

		for (Action action : Action.values()) {
			if (getQ(s, action) > getQ(s, bestAction)) {
				bestAction = action;
			} else if ((getQ(s, action) == getQ(s, bestAction)) && Math.random() > 0.5) {
				bestAction = action;
			}
		}
		return bestAction;
	}
	
	private double getBestQ(State s) {
		double bestQ = -(Double.MAX_VALUE);
		for (Action action : Action.values()) {
			if (getQ(s, action) > bestQ)
				bestQ = getQ(s, action);
		}
		return bestQ;
	}
	
	private double getQ (State s, Action a) {
		//assert(this.isValidState(s));
		return q[s.getX()][s.getY()][a.ordinal()];
	}
}