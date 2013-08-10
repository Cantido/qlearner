package qlearning;

/**
 * An exception that indicates that the goal state has been reached
 * 
 * @author Robert Richter
 */
public class GoalReachedException extends Exception {
	private static final long serialVersionUID = 365110113211897554L;
	private String msg;
	
	public String getMessage() {
		return msg;
	}
	
	public GoalReachedException() {
		msg = new String("Goal state has been reached");
	}

}
