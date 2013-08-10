package qlearning;

/**
 * The position of the q-learning agent
 * 
 * @author Robert Richter
 */
public class QState {

	private int _x;
	private int _y;
	
	private int reward;
	
	public static final int MAX_X = 10;
	public static final int MAX_Y = 7;
	
	private static double[][][] q = new double[MAX_X][MAX_Y][4];
	
	public QState() {
		this._x = 0;
		this._y = 0;
	}

	public QState(QState state) {
		this._x = state.getX();
		this._y = state.getY();
	}
	
	public QState(int x, int y) {
		this._x = x;
		this._y = y;
	}
	
	public int getReward() {
		return reward;
	}
	
	public void setReward(int reward) {
		this.reward = reward;
	}
	
	/**
	 * Returns the q-value for the given state-action pair
	 * 
	 * @param a The action
	 * @return The q-value for a given state-action pair
	 */
	public double getQ(QAction a) {
		return q[_x][_y][a.ordinal()];
	}
	
	/**
	 * @param newQ the new Q value to set for this state-action pair
	 * @param a the action associated with the given q-value
	 */
	public void setQValueForAction(double newQ, QAction a) {
		QState.q[_x][_y][a.ordinal()] = newQ;
	}
	
	/**
	 * @return a random action
	 */
	@SuppressWarnings("static-method")
	public QAction getRandomAction() {
		return QAction.fromOrdinal((int) ((Math.random() * 4)));
	}
	
	/**
	 * Returns the {@link QAction} with the highest Q-value for the given {@link QState}
	 * 
	 * @param s The state for which to find the best action
	 * @return The action with the highest q-value
	 */
	public QAction getBestAction() {
		QAction bestAction = QAction.UP;

		for (QAction action : QAction.values()) {
			if (getQ(action) > getQ(bestAction)) {
				bestAction = action;
			} else if ((getQ(action) == getQ(bestAction)) && Math.random() > 0.5) {
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
	public double getBestQ() {
		double bestQ = -(Double.MAX_VALUE);
		for (QAction action : QAction.values()) {
			if (getQ(action) > bestQ)
				bestQ = getQ(action);
		}
		return bestQ;
	}
	
	/**
	 * Changes the state based on the given action
	 * 
	 * @param a The action to take
	 */
	public void takeAction(QAction a) {
		if (a == QAction.UP && _y != (MAX_Y - 1))
			_y++;
		if (a == QAction.RIGHT && _x != (MAX_X - 1))
			_x++;
		if (a == QAction.DOWN && _y != 0)
			_y--;
		if (a == QAction.LEFT && _x != 0)
			_x--;
		
		/* wind */
		
		switch(_x) {
		case 3:
		case 4:
		case 5:
		case 8:
			if(_y != (MAX_Y - 1))
				_y++;
			break;
		case 6:
		case 7:
			if(_y != (MAX_Y - 1))
				_y++;
			if(_y != (MAX_Y - 1))
				_y++;
			break;
		default:
			break;
		}
	}
	
	public void takeAction(int a) {
		takeAction(QAction.fromOrdinal(a));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QState other = (QState) obj;
		if (_x != other._x)
			return false;
		if (_y != other._y)
			return false;
		return true;
	}

	public int getX() {
		return this._x;
	}

	public int getY() {
		return this._y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _x;
		result = prime * result + _y;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "State [x=" + _x + ", y=" + _y + "]";
	}
}