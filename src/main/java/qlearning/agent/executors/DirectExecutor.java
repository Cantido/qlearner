package qlearning.agent.executors;

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


import java.util.concurrent.Executor;

import org.eclipse.jdt.annotation.Nullable;

/**
 * An {@link Executor} that just runs the given {@link Runnable}
 * in the same thread. Probably the simplest possible implementation.
 */
public class DirectExecutor implements Executor {
    @Override
    public void execute(@Nullable Runnable command) {
        if(command == null) {
            throw new NullPointerException("Cannot execute a null command");
        }
        command.run();
    }
}
