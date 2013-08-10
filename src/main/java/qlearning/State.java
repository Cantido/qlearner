package qlearning;

/**
 * The position of the q-learning agent
 * 
 * @author Robert Richter
 */
class State {

	private int _x;
	private int _y;
	
	public State() {
		this._x = 0;
		this._y = 0;
	}

	public State(State state) {
		this._x = state.getX();
		this._y = state.getY();
	}
	
	public State(int x, int y) {
		this._x = x;
		this._y = y;
	}
	
	/**
	 * Changes the state based on the given action
	 * 
	 * @param a The action to take
	 */
	public void takeAction(Action a) {
		if (a == Action.UP && _y != (QLearner.MAX_Y - 1))
			_y++;
		if (a == Action.RIGHT && _x != (QLearner.MAX_X - 1))
			_x++;
		if (a == Action.DOWN && _y != 0)
			_y--;
		if (a == Action.LEFT && _x != 0)
			_x--;
		
		/* wind */
		
		switch(_x) {
		case 3:
		case 4:
		case 5:
		case 8:
			if(_y != (QLearner.MAX_Y - 1))
				_y++;
			break;
		case 6:
		case 7:
			if(_y != (QLearner.MAX_Y - 1))
				_y++;
			if(_y != (QLearner.MAX_Y - 1))
				_y++;
			break;
		default:
			break;
		}
	}
	
	public void takeAction(int a) {
		takeAction(Action.getActionFromOrdinal(a));
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
		State other = (State) obj;
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