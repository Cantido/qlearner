package qlearning;

/**
 * An exception that indicates that the goal state has been reached
 * 
 * @author Robert Richter
 */
class GoalReachedException extends Exception {
	private static final long serialVersionUID = 365110113211897554L;
	String msg;
	GoalReachedException() {
		msg = new String("Goal state has been reached");
	}

}
