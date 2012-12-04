package qlearning;

class GoalReachedException extends Exception {
	private static final long serialVersionUID = 365110113211897554L;
	String msg;
	GoalReachedException() {
		msg = new String("Goal state has been reached");
	}

}
