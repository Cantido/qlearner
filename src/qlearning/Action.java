package qlearning;

enum Action { 
	UP, 
	RIGHT, 
	DOWN, 
	LEFT;
	
	public static Action getActionFromOrdinal(int ordinal){
		assert(ordinal >= 0 && ordinal <= 3);
		
		Action action = null;
		
		switch(ordinal) {
		case 0:
			action = Action.UP;
			break;
		case 1:
			action = Action.RIGHT;
			break;
		case 2:
			action = Action.DOWN;
			break;
		case 3:
			action = Action.LEFT;
			break;
		default:
			break;
		};
		return action;
	}
	
	public static String toString(Action action){
		if(action == Action.UP)
			return ("UP");
		else if(action == Action.RIGHT)
			return ("RIGHT");
		else if(action == Action.DOWN)
			return ("DOWN");
		else if(action == Action.LEFT)
			return ("LEFT");
		else
			return ("INVALID STATE");
	}
}