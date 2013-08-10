package qlearning;

/**
 * Represents an action that can be taken
 * 
 * @author Robert Richter
 */
public enum QAction { 
	UP, 
	RIGHT, 
	DOWN, 
	LEFT;
	
	/**
	 * Gets an Action from an ordinal integer
	 * 
	 * @param ordinal The number of the action to get
	 * @return The action with the given ordinal
	 */
	public static QAction fromOrdinal(int ordinal){
		assert(ordinal >= 0 && ordinal <= 3);
		
		QAction action = null;
		
		switch(ordinal) {
			case 0:
				return UP;
			case 1:
				return RIGHT;
			case 2:
				return DOWN;
			case 3:
				return LEFT;
			default:
				break;
		};
		return action;
	}
}