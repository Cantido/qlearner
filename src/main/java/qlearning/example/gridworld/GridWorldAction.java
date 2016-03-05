package qlearning.example.gridworld;

import org.eclipse.jdt.annotation.NonNull;

/*
 * #%L
 * QLearner
 * %%
 * Copyright (C) 2012 - 2016 Robert Richter
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qlearning.Action;

public enum GridWorldAction implements Action {
    UP {
        @Override
        protected void performMove(@NonNull GridWorldEnvironment environment) {
            environment.moveUp();
        }
    },
    DOWN {
        @Override
        protected void performMove(@NonNull GridWorldEnvironment environment) {
            environment.moveDown();
        }
    },
    LEFT {
        @Override
        protected void performMove(@NonNull GridWorldEnvironment environment) {
            environment.moveLeft();
        }
    },
    RIGHT {
        @Override
        protected void performMove(@NonNull GridWorldEnvironment environment) {
            environment.moveRight();
        }
    };
    

    @SuppressWarnings("null")
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Nullable private static GridWorldEnvironment gridWorld;
    
    protected abstract void performMove(GridWorldEnvironment environment);

    public static void setGridWorld(GridWorldEnvironment gw) {
        gridWorld = gw;
    }

    @Override
    public void execute() {
        final GridWorldEnvironment gridWorld = GridWorldAction.gridWorld;
        if(gridWorld == null) {
            throw new IllegalStateException("Grid World Environment was not initalized yet");
        }
        
        logger.debug("Executing GridWorldAction {}", this);
        
        performMove(gridWorld);
    }
}
