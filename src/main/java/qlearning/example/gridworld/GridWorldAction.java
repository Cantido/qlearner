package qlearning.example.gridworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.Action;

public enum GridWorldAction implements Action {
    UP, DOWN, LEFT, RIGHT;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static GridWorldEnvironment gridWorld;

    public static void setGridWorld(GridWorldEnvironment gw) {
        gridWorld = gw;
    }

    @Override
    public void execute() {
        logger.debug("Executing GridWorldAction {}", this);
        switch (this) {
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
