package qlearning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum GridWorldAction implements Action {
	UP, DOWN, LEFT, RIGHT;

	Logger logger = LoggerFactory.getLogger("qlearner.GridWorldAction");
	
	private static GridWorld gridWorld;
	
	public static void setGridWorld(GridWorld gw) {
		gridWorld = gw;
	}

	@Override
	public void execute() {
		logger.debug("Executing GridWorldAction {}", this);
		switch(this) {
			case UP:
				gridWorld.moveUp();
				break;
			case DOWN:
				gridWorld.moveDown();
				break;
			case LEFT:
				gridWorld.moveLeft();
				break;
			case RIGHT:
				gridWorld.moveRight();
				break;
			default:
				throw new IllegalStateException("GridWorldAction not equal to any direction: " + this);
		}
	}
}
