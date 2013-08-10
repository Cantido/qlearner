package qlearning;

/**
 * Represents an action that can be taken
 * 
 * @author Robert Richter
 */
enum QAction { 
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
	public static QAction getActionFromOrdinal(int ordinal){
		assert(ordinal >= 0 && ordinal <= 3);
		
		QAction action = null;
		
		switch(ordinal) {
		case 0:
			action = QAction.UP;
			break;
		case 1:
			action = QAction.RIGHT;
			break;
		case 2:
			action = QAction.DOWN;
			break;
		case 3:
			action = QAction.LEFT;
			break;
		default:
			break;
		};
		return action;
	}
	
	public static String toString(QAction action){
		if(action == QAction.UP)
			return ("UP");
		else if(action == QAction.RIGHT)
			return ("RIGHT");
		else if(action == QAction.DOWN)
			return ("DOWN");
		else if(action == QAction.LEFT)
			return ("LEFT");
		else
			return ("INVALID STATE");
	}
}